# TRAM SCHEDULE BOT
## General Infromation

TRAM SCHEDULE BOT is a Telegram bot, the purpose of which is to parse data on the timetable
of trams with routes "2" and "8", based on the selected direction and weekdays/weekends using the buttons from the bot's menu.

## HOW TO START?
Before launching this bot, make sure that all programs are installed on your system.
List of programs:
- Git;
- Docker.

Then clone this repository by ```git clone https://github.com/DER-2SH-KA/TramScheduleBot.git``` command.

Great. After cloning the repository, you must go to repositories folder and create file with name ```.env```. 
Purpose this file is give Docker Compose information about Telegram Bot Token. Just write it in ```.env``` file stoke like 
```TG_TOKEN=* YOUR TELEGRAM BOT'S TOKEN WITHOUT STRARS AND SPACES *``` and save this file.

Please, check docker compose config by ```docker-compose config``` command. You should see in terminal or console the string with your 
Telegram bot's token here.

If previous steps was successful, you already to start bot. Just let this steps:
1. Run Docker on your system;
2. Check if Docker is running with the command ```docker ps``` in the terminal or console;
3. Write this command and wait ```docker-compose -p app up -d --build```.

If all was successful, you should see string what Docker Compose container was created and started successfully.
