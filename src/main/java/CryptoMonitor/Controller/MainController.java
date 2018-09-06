package CryptoMonitor.Controller;

import CryptoMonitor.Service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    MonitoringService monitoringService;

    @GetMapping("/api/data/getHBTC")
    @CrossOrigin
    @ResponseBody
    public Double sendHBTC() {
        return monitoringService.getHBTC_VALUE();
    }

    @GetMapping("/api/data/getPLNX")
    @CrossOrigin
    @ResponseBody
    public Double sendPLNX() {
        return monitoringService.getPLNX_VALUE();
    }

    @GetMapping("/api/data/getPlnxToHbtc")
    @CrossOrigin
    @ResponseBody
    public Double sendPlnxToHbtc() {
        return monitoringService.getPlnxToHbtc();
    }

    @GetMapping("/api/data/getHbtcToPlnx")
    @CrossOrigin
    @ResponseBody
    public Double sendHbtcToPlnx() {
        return monitoringService.getHbtcToPlnx();
    }

}
