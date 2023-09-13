@echo off
:loop
java -jar svcSendPanel.jar 192.168.1.222 5200 "https://editor.mobilibus.com/web/get-proximas-partidas/54byr/386641"
timeout /t 90
goto loop