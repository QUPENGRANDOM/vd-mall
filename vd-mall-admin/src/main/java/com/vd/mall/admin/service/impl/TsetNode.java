package com.vd.mall.admin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;

public class TsetNode {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DataBaseNode dataBaseNode = new DataBaseNode("w_", format.parse("2018-01-11"), format.parse("2020-02-11"));
        dataBaseNode.init();
        List<String> dataNodes = dataBaseNode.queryDataNode(format.parse("2020-01-11"), format.parse("2020-11-11"));
        System.out.println(dataNodes);
    }

    private static String buildName(LocalDate localDate, String prefix) {
        return String.format("%s%s_%s", prefix, localDate.getYear(), quarter(localDate));
    }

    private static int quarter(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return quarter(localDate);
    }

    private static int quarter(LocalDate localDateTime) {
        int month = localDateTime.getMonth().getValue();
        if (month <= 3)
            return 1;

        if (month <= 6)
            return 2;

        if (month <= 9)
            return 3;

        return 4;
    }

    public static class DataBaseNode {
        private static final ZoneId zone = ZoneId.systemDefault();
        private String prefix;
        private Date nodeStart;
        private Date nodeEnd;

        private Map<String, Integer> indexMappings = new HashMap<>();
        private List<String> dataBases = new ArrayList<>();

        public DataBaseNode(String prefix, Date nodeStart, Date nodeEnd) {
            this.nodeStart = nodeStart;
            this.nodeEnd = nodeEnd;
            this.prefix = prefix;
        }

        public List<String> queryDataNode(Date begin, Date end) {
            int startIndex = indexMappings.get(buildName(convert(begin), prefix));
            int endIndex = indexMappings.get(buildName(convert(end), prefix));
            List<String> nodes = new ArrayList<>();
            for (; startIndex <= endIndex; startIndex++) {
                nodes.add(dataBases.get(startIndex));
            }

            return nodes;
        }

        public String queryDataNode(Date date) {
            int index = indexMappings.get(buildName(convert(date), prefix));
            return dataBases.get(index);
        }

        public void init() {
            LocalDate start = convert(nodeStart);
            LocalDate end = convert(nodeEnd);
            int startYear = start.getYear(), endYear = end.getYear();
            int startQuarter = quarter(start), endQuarter = quarter(end);

            int index = 0;
            while (startYear <= endYear) {
                int look = startYear == endYear ? endQuarter : 4;
                for (; startQuarter <= look; startQuarter++) {
                    String database = String.format("%s%s_%s", prefix, startYear, startQuarter);
                    dataBases.add(database);
                    indexMappings.put(database, index);
                    index++;
                }
                startYear++;
                startQuarter = 1;
            }
        }

        private LocalDate convert(Date date) {
            return LocalDateTime.ofInstant(date.toInstant(), zone).toLocalDate();
        }
    }


}
