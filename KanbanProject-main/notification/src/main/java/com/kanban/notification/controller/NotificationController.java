package com.kanban.notification.controller;

import com.kanban.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getNotification(@PathVariable String name) {
        return new ResponseEntity<>(notificationService.getNotification(name), HttpStatus.OK);
    }

    @GetMapping("/allRead/{name}")
    public ResponseEntity<?> markAllAsRead(@PathVariable String name) {
        return new ResponseEntity<>(notificationService.markAllRead(name), HttpStatus.OK);
    }

    @GetMapping("/read/{name}/{message}")
    public ResponseEntity<?> markAsRead(@PathVariable String name, @PathVariable String message) {
        return new ResponseEntity<>(notificationService.markRead(name, message), HttpStatus.OK);
    }
}
