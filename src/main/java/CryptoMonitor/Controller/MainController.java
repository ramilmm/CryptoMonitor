package CryptoMonitor.Controller;

import CryptoMonitor.Service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class MainController {

    @Autowired
    MonitoringService monitoringService;

    @GetMapping("/")
    public String render(){

        return "Hello World!";
    }

    @GetMapping("/test")
    public String checkApp() {
        monitoringService.refreshData();
        return "Working!";
    }




}
