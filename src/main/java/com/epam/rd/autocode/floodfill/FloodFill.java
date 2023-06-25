package com.epam.rd.autocode.floodfill;

import java.util.ArrayList;

public interface FloodFill {
    void flood(final String map, final FloodLogger logger);

    static FloodFill getInstance() {
        FloodFill floodFill = new FloodFill() {
            @Override
            public void flood(String map, FloodLogger logger) {
                String result = "";
                logger.log(map);

                if (map.indexOf(LAND) == -1) {
                    return;
                }

                if (!map.contains("\n")) {

                    char[] simvols = new char[map.length()];
                    for (int i = 0; i < map.length(); i++) {
                        simvols[i] = map.charAt(i);
                    }

                    ArrayList<Integer> waterSimvols = new ArrayList<>();
                    for (int i = 0; i < simvols.length; i++) {
                        if (simvols[i] == WATER) {
                            waterSimvols.add(i);
                        }
                    }

                    for (int i = 0; i < waterSimvols.size(); i++) {
                        int x = waterSimvols.get(i);
                        if (x == 0) {
                            simvols[x + 1] = WATER;
                        }
                        if (x == simvols.length - 1) {
                            simvols[x - 1] = WATER;
                        }
                        if (x != 0 && x != simvols.length - 1) {
                            simvols[x + 1] = WATER;
                            simvols[x - 1] = WATER;
                        }
                    }
                    for (int i = 0; i < simvols.length; i++) {
                        result += simvols[i];
                    }
                } else {

                    String[] strings = map.split("\n");

                    int max = 0;
                    for (String string : strings) {
                        if (string.length() > max) {
                            max = string.length();
                        }
                    }

                    char[][] simvol = new char[strings.length][max];
                    int i = 0;
                    for (String string : strings) {
                        for (int j = 0; j < max; j++) {
                            simvol[i][j] = string.charAt(j);
                        }
                        i++;
                    }

                    ArrayList<Integer> waterCharIndex = new ArrayList<>();

                    for (int j = 0; j < strings.length; j++) {
                        for (int k = 0; k < max; k++) {
                            if (simvol[j][k] == WATER) {
                                waterCharIndex.add(j);
                                waterCharIndex.add(k);
                            }
                        }
                    }

                    for (int j = 0; j < waterCharIndex.size() - 1;) {
                        int y = waterCharIndex.get(j);
                        int x = waterCharIndex.get(++j);

                        // for first line

                        if (y == 0 && x == 0) {
                            simvol[y][x + 1] = WATER;
                            simvol[y + 1][x] = WATER;
                        }

                        if (y == 0 && x != 0 && x != max - 1) {
                            simvol[y][x - 1] = WATER;
                            simvol[y][x + 1] = WATER;
                            simvol[y + 1][x] = WATER;
                        }

                        if (y == 0 && x == max - 1) {
                            simvol[y][x - 1] = WATER;
                            simvol[y + 1][x] = WATER;
                        }

                        // for last line

                        if (y == strings.length - 1 && x == 0) {
                            simvol[y][x + 1] = WATER;
                            simvol[y - 1][x] = WATER;
                        }

                        if (y == strings.length - 1 && x != 0 && x != max - 1) {
                            simvol[y][x - 1] = WATER;
                            simvol[y][x + 1] = WATER;
                            simvol[y - 1][x] = WATER;
                        }

                        if (y == strings.length - 1 && x == max - 1) {
                            simvol[y][x - 1] = WATER;
                            simvol[y - 1][x] = WATER;
                        }

                        // for middle lines

                        if (y != 0 && y != strings.length - 1 && x == 0) {
                            simvol[y + 1][x] = WATER;
                            simvol[y][x + 1] = WATER;
                            simvol[y - 1][x] = WATER;
                        }

                        if (y != 0 && y != strings.length - 1 && x == max - 1) {
                            simvol[y + 1][x] = WATER;
                            simvol[y][x - 1] = WATER;
                            simvol[y - 1][x] = WATER;
                        }

                        if (y != 0 && y != strings.length - 1 && x != 0 && x != max - 1) {
                            simvol[y + 1][x] = WATER;
                            simvol[y - 1][x] = WATER;
                            simvol[y][x + 1] = WATER;
                            simvol[y][x - 1] = WATER;
                        }
                        j++;

                    }
                    for (int j = 0; j < strings.length; j++) {
                        for (int k = 0; k < max; k++) {
                            result += simvol[j][k];
                        }
                        result += "\n";
                    }
                    result = result.substring(0, result.length() - 1);
                }

                if (!map.equals(result)) {
                    flood(result, logger);
                }
            }
        };
        return floodFill;
    }

    char LAND = '█';
    char WATER = '░';
}
