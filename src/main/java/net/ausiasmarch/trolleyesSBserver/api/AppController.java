/*
 * Copyright (c) 2020
 *
 * by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com) & 2020 DAW students
 *
 * TROLLEYES: Free Open Source Shopping Site
 *
 *
 * Sources at:                https://github.com/rafaelaznar/trolleyesSBserver
 * Database at:               https://github.com/rafaelaznar/trolleyesSBserver
 * Client at:                 https://github.com/rafaelaznar/TrolleyesAngularJSClient
 *
 * TROLLEYES is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ausiasmarch.trolleyesSBserver.api;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {

    @Value("${i_date}")
    private String i_date;
    @Value("${i_version}")
    private String i_version;
    @Value("${i_author}")
    private String i_author;
    @Value("${i_license}")
    private String i_license;
    @Value("${i_sources}")
    private String i_sources;
    @Value("${i_name}")
    private String i_name;

    private @Autowired
    HttpServletRequest oRequest;

    @Autowired
    Environment oEnvironment;

    @GetMapping("/")
    public ResponseEntity<?> info() {
        String strInfo = "<!DOCTYPE html>\n";
        strInfo += "<html>\n";
        strInfo += "<head>\n";
        strInfo += "<title>" + i_name + " SERVER</title></title>\n";
        strInfo += "<style>\n";
        strInfo += "table, th, td {border: 1px solid black;padding:7px;}\n";
        strInfo += "body{\n"
                + "    background-repeat:no-repeat;\n"
                + "    background-position: center center;\n"
                + "    background-image:url(../images/trolleyes3.png);\n"
                + "    opacity: 0.8;\n"
                + "    font-family:Arial, Helvetica, sans-serif;\n"
                + "    font-size:20px;\n"
                + "    min-height:100%;\n"
                + "}\n";
        strInfo += "</style>\n";
        strInfo += "</head>\n";
        strInfo += "<body>\n";
        strInfo += "<h1>Welcome to " + i_name + " server</h1>\n";
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();
        long millis = uptime % 1000;
        long second = (uptime / 1000) % 60;
        long minute = (uptime / (1000 * 60)) % 60;
        long hour = (uptime / (1000 * 60 * 60)) % 24;
        String time = String.format("%02dh:%02dm:%02ds.%dms", hour, minute, second, millis);
        strInfo += "<h2>Server listening for " + time + "</h2>";
        try {
            // Local address
            strInfo += "<table>";
            strInfo += "<tr><th>LOCAL Addr:</th><td>" + InetAddress.getLocalHost().getHostAddress() + "</td></tr>";
            strInfo += "<tr><th>LOCAL Host:</th><td>" + InetAddress.getLocalHost().getHostName() + "</td></tr>";

            // Remote address
            strInfo += "<tr><th>REMOTE Addr:</th><td>" + InetAddress.getLoopbackAddress().getHostAddress() + "</td></tr>";
            strInfo += "<tr><th>REMOTE Host:</th><td>" + InetAddress.getLoopbackAddress().getHostName() + "</td></tr>";
        } catch (UnknownHostException ex) {
            //Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        strInfo += "<tr><th>Version:</th><td>" + i_version + "</td></tr>";;
        strInfo += "<tr><th>Date:</th><td>" + i_date + "</td></tr>";
        strInfo += "<tr><th>Author:</th><td>" + i_author + "</td></tr>";
        strInfo += "<tr><th>License:</th><td>" + i_license + "</td></tr>";
        strInfo += "<tr><th>Sources:</th><td>" + i_sources + "</td></tr>\n";
        strInfo += "</body>\n";
        strInfo += "</html>\n";
        return new ResponseEntity<String>(strInfo, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<String> shortInfo() {
        return new ResponseEntity<String>("Welcome to TROLLEYES Server", HttpStatus.OK);
    }

}
