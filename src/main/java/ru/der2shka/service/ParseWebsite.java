package ru.der2shka.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class ParseWebsite {
    private static final ExecutorService es = Executors.newVirtualThreadPerTaskExecutor();

    public static CompletableFuture<String> parseWebsite(
            String route,
            String direction,
            String schedule
    ) {
        return CompletableFuture.supplyAsync(() -> {
            String url = String.format(
                    "https://nskgortrans.ru/components/com_planrasp/helpers/grasp.php?tv=mr&m=%s&t=3&r=%s&sch=%s&s=0&v=0",
                    route, direction, schedule
            );

            StringBuilder result = new StringBuilder();

            try {
                Document doc = Jsoup.connect(url).get();
                Elements all_table = doc.select("tr");

                Element second_row_with_schedule = all_table.get(1);
                Elements h2_schedule = second_row_with_schedule.select("h2");
                Elements table_schedule = second_row_with_schedule.select("table");

                String firstStationName = h2_schedule.first().text();
                String lastStationName = h2_schedule.last().text();

                Elements firstStationHours = table_schedule.first().select(".td_plan_h");
                Elements firstStationMinutes = table_schedule.first().select(".td_plan_m");

                Elements lastStationHours = table_schedule.last().select(".td_plan_h");
                Elements lastStationMinutes = table_schedule.last().select(".td_plan_m");

                switch (schedule) {
                    case "11":
                        result.append("Расписание: Будни\n");
                        break;
                    case "5":
                        result.append("Расписание: Выходные\n");
                        break;
                    default:
                        result.append("Расписание: Не ясно\n");
                        break;
                }

                int i_h = 0;
                result.append(firstStationName + "\n");
                for (int i_m = 0; i_m < firstStationHours.size(); i_m++) {
                    if ( !firstStationMinutes.get(i_m).text().trim().isEmpty() ) {
                        result
                            .append("Час: " + firstStationHours.get(i_h).text().trim() + " ")
                            .append("Минуты: " + firstStationMinutes.get(i_m).text().trim() + "\n");
                        i_h++;
                    }
                }
                result.append("\n");

                i_h = 0;
                result.append(lastStationName + "\n");
                for (int i_m = 0; i_m < lastStationMinutes.size(); i_m++) {
                    if ( !lastStationMinutes.get(i_m).text().trim().isEmpty() ) {
                        result
                                .append("Час: " + lastStationHours.get(i_h).text().trim() + " ")
                                .append("Минуты: " + lastStationMinutes.get(i_m).text().trim() + "\n");
                        i_h++;
                    }
                }

                // result.append(firstStation).append("\n").append(lastStation).append("\n");
            }
            catch (Exception ex) { ex.printStackTrace(); }

            return result.toString();
        }, es);
    }
}
