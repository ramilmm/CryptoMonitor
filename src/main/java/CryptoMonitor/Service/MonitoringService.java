package CryptoMonitor.Service;

import CryptoMonitor.Model.CurrencyTemplate;
import CryptoMonitor.Model.ExchangeData;
import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

@Service
public class MonitoringService {

    private Properties prop = new Properties();
    private InputStream input = null;

    private String PLNX_API_HOST, PLNX_API_METHOD, PLNX_CURRENCY;
    private String HBTC_API_HOST, HBTC_API_METHOD, HBTC_CURRENCY;
    private Integer DEPTH;
    private HttpClient client;
    private StringBuilder api = new StringBuilder();
    private volatile Double hbtcToPlnx, plnxToHbtc;
    private volatile Double HBTC_VALUE, PLNX_VALUE;


    @PostConstruct
    public void init() {
        try {
            input = new FileInputStream("conf/config.properties");

            prop.load(input);

            PLNX_API_HOST = prop.getProperty("PLNX_API_HOST");
            PLNX_API_METHOD = prop.getProperty("PLNX_API_METHOD");
            PLNX_CURRENCY = prop.getProperty("PLNX_CURRENCY");

            HBTC_API_HOST = prop.getProperty("HBTC_API_HOST");
            HBTC_API_METHOD = prop.getProperty("HBTC_API_METHOD");
            HBTC_CURRENCY = prop.getProperty("HBTC_CURRENCY");

            DEPTH = Integer.valueOf(prop.getProperty("DEPTH"));

        } catch (IOException e) {
            e.getMessage();
        }

        client = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
    }

    @Scheduled(fixedDelay = 1000)
    public void refreshData() {

        ExchangeData plnx = parsePoloniexJson();
        ExchangeData hbtc = parseHitBTCJson();

        HBTC_VALUE = hbtc.getAsk().get(0).getPrice();
        PLNX_VALUE = plnx.getAsk().get(0).getPrice();

        plnxToHbtc = plnx.getBid().get(0).getPrice() - hbtc.getAsk().get(0).getPrice();
        hbtcToPlnx = hbtc.getBid().get(0).getPrice() - plnx.getAsk().get(0).getPrice();

    }

    private ExchangeData parsePoloniexJson() {

        api.setLength(0);

        api.append(PLNX_API_HOST).append("command=").append(PLNX_API_METHOD)
                .append("&currencyPair=").append(PLNX_CURRENCY)
                .append("&depth=").append(DEPTH);

        String jsonText = makeRequest(api);

        ExchangeData exchangeData = new ExchangeData();
        ArrayList<CurrencyTemplate> asksArray = new ArrayList<>();
        ArrayList<CurrencyTemplate> bidsArray = new ArrayList<>();


        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = jsonParser.parse(jsonText).getAsJsonObject();

        JsonArray array = jsonObject.getAsJsonArray("asks");

        for (JsonElement ask : array) {
            JsonArray askArray = ask.getAsJsonArray();

            asksArray.add(new CurrencyTemplate(
                    Double.valueOf(askArray.get(0).toString().replaceAll("\"", "")),
                    Double.valueOf(askArray.get(1).toString())));
        }

        array = jsonObject.getAsJsonArray("bids");


        for (JsonElement ask : array) {
            JsonArray askArray = ask.getAsJsonArray();
            bidsArray.add(new CurrencyTemplate(
                    Double.valueOf(askArray.get(0).toString().replaceAll("\"", "")),
                    Double.valueOf(askArray.get(1).toString())));
        }

        exchangeData.setAsk(asksArray);
        exchangeData.setBid(bidsArray);

        return exchangeData;
    }

    public ExchangeData parseHitBTCJson() {
        api.setLength(0);
        api.append(HBTC_API_HOST).append(HBTC_API_METHOD)
                .append(HBTC_CURRENCY).append("?limit=").append(DEPTH);

        String jsonText = makeRequest(api);

        Gson gson = new Gson();

        return gson.fromJson(jsonText, ExchangeData.class);
    }

    private String makeRequest(StringBuilder api) {

        HttpGet request = new HttpGet(String.valueOf(api));

        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String responseString = "";

        try {
            responseString = new BasicResponseHandler().handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString;
    }

    public Double getHBTC_VALUE() {
        return HBTC_VALUE;
    }

    public Double getPLNX_VALUE() {
        return PLNX_VALUE;
    }

    public Double getHbtcToPlnx() {
        return hbtcToPlnx;
    }

    public Double getPlnxToHbtc() {
        return plnxToHbtc;
    }
}
