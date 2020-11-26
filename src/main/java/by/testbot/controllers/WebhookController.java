package by.testbot.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.testbot.services.WebhookService;

@RestController
public class WebhookController {
    @Autowired
    private WebhookService webhookService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void onUpdateReceived(@RequestBody String update) {
        webhookService.onWebhookUpdateReceived(new JSONObject(update));
    }
}
