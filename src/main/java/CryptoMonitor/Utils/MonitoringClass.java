//package CryptoMonitor.Utils;
//
//import CryptoMonitor.Model.CurrencyTemplate;
//import CryptoMonitor.Model.ExchangeData;
//import com.google.gson.*;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.CookieSpecs;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.HttpClients;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class MonitoringClass {
//
//    private static String API_HOST = "https://poloniex.com/public?";
//    private static String API_METHOD = "returnOrderBook";
//    private static String CURRENCY = "BTC_ETH";
//    private static Integer DEPTH = 1;
//    private static HttpClient client;
//    private static StringBuilder api = new StringBuilder();
//
//    public static void main(String[] args) {
//
//        client = HttpClients.custom()
//                .setDefaultRequestConfig(RequestConfig.custom()
//                        .setCookieSpec(CookieSpecs.STANDARD).build())
//                .build();
//
//
//        System.out.println(parseHitBTCJson());
//        System.out.println(parsePoloniexJson());
//
//
//    }
//
//    private static ExchangeData parsePoloniexJson() {
//
//        api.setLength(0);
//
//        api.append(API_HOST).append("command=").append(API_METHOD)
//                .append("&currencyPair=").append(CURRENCY)
//                .append("&depth=").append(DEPTH);
//
//        String jsonText = makeRequest(api);
//
//        ExchangeData exchangeData = new ExchangeData();
//        ArrayList<CurrencyTemplate> asksArray = new ArrayList<>();
//        ArrayList<CurrencyTemplate> bidsArray = new ArrayList<>();
//
//
//        JsonParser jsonParser = new JsonParser();
//
//        JsonObject jsonObject = jsonParser.parse(jsonText).getAsJsonObject();
//
//        JsonArray array = jsonObject.getAsJsonArray("asks");
//
//        for (JsonElement ask : array) {
//            JsonArray askArray = ask.getAsJsonArray();
//
//            asksArray.add(new CurrencyTemplate(
//                    Double.valueOf(askArray.get(0).toString().replaceAll("\"", "")),
//                    Double.valueOf(askArray.get(1).toString())));
//        }
//
//        array = jsonObject.getAsJsonArray("bids");
//
//
//        for (JsonElement ask : array) {
//            JsonArray askArray = ask.getAsJsonArray();
//            bidsArray.add(new CurrencyTemplate(
//                    Double.valueOf(askArray.get(0).toString().replaceAll("\"", "")),
//                    Double.valueOf(askArray.get(1).toString())));
//        }
//
//        exchangeData.setAsk(asksArray);
//        exchangeData.setBid(bidsArray);
//
//        return exchangeData;
//    }
//
//    public static ExchangeData parseHitBTCJson() {
//
//        api.setLength(0);
//
//        api.append("https://api.hitbtc.com/api/2/public/orderbook/ETHBTC");
//
//        String jsonText = makeRequest(api);
//
//        Gson gson = new Gson();
//
//        return gson.fromJson(jsonText, ExchangeData.class);
//    }
//
//    private static String makeRequest(StringBuilder api) {
//
//        HttpGet request = new HttpGet(String.valueOf(api));
//
//        HttpResponse response = null;
//        try {
//            response = client.execute(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String responseString = "";
//
//        try {
//            responseString = new BasicResponseHandler().handleResponse(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return responseString;
//    }
//}
