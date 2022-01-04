package com.test.abc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sawmills {

    /*Gets Input from user*/
    public String inputReader(String input) {
        String[] lines = input.split("\n");
        return prepareInputFacotry(lines, 0, -1, new StringBuilder(), 0, null);
    }

    /*Gets Input and Iterates Between lines*/
    private String prepareInputFacotry(String[] lines, int lineNumber, int factoryNumber, StringBuilder result, int caseNumber, String[] riverLines) {
        if (factoryNumber == 1) {
            return result.toString();
        } else if (factoryNumber > 1) {
            if (lines[lineNumber].length() > 1) {
                riverLines = Arrays.copyOfRange(lines, lineNumber, lineNumber + factoryNumber - 1);
                caseNumber = caseNumber + 1;
                result = findMaxProfitAndBestOrders(riverLines, 0, null, 0
                        , new StringBuilder(), 0, caseNumber, result, 0,
                        0, factoryNumber - 2, false, new ArrayList<>(), 0, 0);
                lineNumber = lineNumber + riverLines.length;
                riverLines = null;
                return prepareInputFacotry(lines, lineNumber, factoryNumber, result, caseNumber, riverLines);
            }

        }
        if (lines[lineNumber].length() == 1) {
            factoryNumber = Integer.parseInt(lines[lineNumber]) + 1;
            lineNumber = lineNumber + 1;
            return prepareInputFacotry(lines, lineNumber, factoryNumber, result, caseNumber, riverLines);
        }
        return prepareInputFacotry(lines, lineNumber, factoryNumber, result, caseNumber, riverLines);

    }

    /*Find Order For Best Combination for Trunks in river and calculate max Profit */
    private StringBuilder findMaxProfitAndBestOrders(String[] riverLines, int numberOfTrunks, int[] river, int profit
            , StringBuilder riverResult, int riverIndex, int caseNumber, StringBuilder result, int lastProfit, int startLineIndex
            , int lineProcessorIndex, boolean lineProcessed, List<List<Integer>> bestCombinationTrunks, int maxProfit, int innerProfit) {
        /*iterate for multiple lines*/
        if (riverLines.length > 1 && !lineProcessed) {
            String riverLine = riverLines[startLineIndex];
            numberOfTrunks = Integer.parseInt(String.valueOf(riverLine.charAt(0)));
            river = Arrays.stream(riverLine.substring(2).split(" ")).mapToInt(Integer::parseInt).toArray();

            return findMaxProfitAndBestOrders(riverLines, numberOfTrunks, river, profit, riverResult, 0, caseNumber
                    , result, lastProfit, startLineIndex, lineProcessorIndex, true, new ArrayList<>(), maxProfit, 0);
        } else if (riverLines.length == 1 && !lineProcessed) {
            numberOfTrunks = Integer.parseInt(String.valueOf(riverLines[0].charAt(0)));
            river = Arrays.stream(riverLines[0].substring(2).split(" ")).mapToInt(Integer::parseInt).toArray();
            return findMaxProfitAndBestOrders(riverLines, numberOfTrunks, river, profit, riverResult, 0, caseNumber, result
                    , lastProfit, startLineIndex, lineProcessorIndex, true, new ArrayList<>(), maxProfit, 0);
        }
        /*create first list to get the best possible answers*/
        if (bestCombinationTrunks.isEmpty()) {
            List<Integer> fristBestCombinationTrunck = new ArrayList<>();
            fristBestCombinationTrunck.add(river[riverIndex]);
            fristBestCombinationTrunck.add(river[riverIndex + 1]);
            int firstTempNumber = 0;
            int secondTempNumber = 0;
            int thirdTempNumber = 0;
            int forthTempNumber = 0;
            int firstProfit = 0;
            int secondProfit = 0;
            firstTempNumber = fristBestCombinationTrunck.get(0) - 3;
            secondTempNumber = fristBestCombinationTrunck.get(1) - Math.abs(firstTempNumber);
            firstProfit = getProfit(fristBestCombinationTrunck.get(0), 0);
            firstProfit = getProfit(firstTempNumber, firstProfit);
            firstProfit = getProfit(secondTempNumber, firstProfit);
            thirdTempNumber = fristBestCombinationTrunck.get(1) - 3;
            forthTempNumber = fristBestCombinationTrunck.get(0) - Math.abs(thirdTempNumber);
            secondProfit = getProfit(fristBestCombinationTrunck.get(1), 0);
            secondProfit = getProfit(thirdTempNumber, secondProfit);
            secondProfit = getProfit(forthTempNumber, secondProfit);
            if (firstProfit < secondProfit) {
                Collections.swap(fristBestCombinationTrunck, 1, 0);
                lastProfit = firstProfit;
                bestCombinationTrunks.add(fristBestCombinationTrunck);
            } else if (firstProfit > secondProfit) {
                lastProfit = firstProfit;
                bestCombinationTrunks.add(fristBestCombinationTrunck);
            } else {
                bestCombinationTrunks.add(fristBestCombinationTrunck);
                List<Integer> secondBestCombination = new ArrayList<>(fristBestCombinationTrunck);
                Collections.swap(secondBestCombination, 0, 1);
                bestCombinationTrunks.add(secondBestCombination);
                String maxLine = Arrays.toString(fristBestCombinationTrunck.toArray());

            }
            riverIndex = riverIndex + 1;
            return findMaxProfitAndBestOrders(riverLines, numberOfTrunks, river, profit, riverResult
                    , riverIndex, caseNumber, result, lastProfit, startLineIndex, lineProcessorIndex
                    , lineProcessed, bestCombinationTrunks, maxProfit, innerProfit);
        } else {
            /*create first list may get best answer*/
            int leftOver = 0;
            for (int n = 0; n < bestCombinationTrunks.size(); n++) {
                for (int i = 0; i < numberOfTrunks; i++) {
                    boolean increaseRiver = false;
                    if (bestCombinationTrunks.get(n).size() < numberOfTrunks) {
                        if (riverIndex < numberOfTrunks - 1)
                            riverIndex = riverIndex + 1;
                        bestCombinationTrunks.get(n).add(river[riverIndex]);
                        increaseRiver = true;
                    }
                    for (int j = 0; j < bestCombinationTrunks.get(n).size(); j++) {
                        if (j + 1 < bestCombinationTrunks.get(n).size() - 1) {
                            if (bestCombinationTrunks.get(n).get(j) == 1 && bestCombinationTrunks.get(n).get(j + 1) == 1) {
                                break;
                            }
                        }
                        if (leftOver == 0) {
                            if (bestCombinationTrunks.get(n).get(j) < 5) {
                                leftOver = bestCombinationTrunks.get(n).get(j) - 3;
                                profit = getProfit(bestCombinationTrunks.get(n).get(j), profit);
                            } else {
                                int numberOfCut = bestCombinationTrunks.get(n).get(j) / 3;
                                for (int k = 0; k < numberOfCut; k++) {
                                    profit = getProfit(3, profit);
                                }
                                leftOver = bestCombinationTrunks.get(n).get(j) % 3;
                            }

                        } else {
                            if (leftOver < 0) {
                                if (bestCombinationTrunks.get(n).get(j) > Math.abs(leftOver)) {
                                    profit = getProfit(leftOver, profit);
                                    profit = getProfit(bestCombinationTrunks.get(n).get(j) - Math.abs(leftOver), profit);
                                    leftOver = 0;
                                } else if (bestCombinationTrunks.get(n).get(j) < Math.abs(leftOver)) {
                                    profit = getProfit(bestCombinationTrunks.get(n).get(j), profit);
                                    leftOver = bestCombinationTrunks.get(n).get(j) - Math.abs(leftOver);
                                } else {
                                    profit = getProfit(leftOver, profit);
                                    leftOver = 0;
                                }

                            } else {
                                profit = getProfit(leftOver, profit);
                                profit = getProfit(bestCombinationTrunks.get(n).get(j), profit);
                            }
                        }
                    }
                    leftOver = 0;
                    if (profit > lastProfit || increaseRiver) {
                        lastProfit = profit;
                        String max = Arrays.toString(bestCombinationTrunks.get(n).toArray());
                        if (!riverResult.toString().contains(max)) {
                            innerProfit = profit;
                            riverResult.append(" ").append(max);
                        }
                    }
                    if (profit == lastProfit) {
                        String max = Arrays.toString(bestCombinationTrunks.get(n).toArray());
                        if (!riverResult.toString().contains(max)) {
                            innerProfit = profit;
                            riverResult.append(" ").append(max);
                        }
                    }
                    if (i != riverIndex) {
                        Collections.swap(bestCombinationTrunks.get(n), i, riverIndex);
                        profit = 0;
                    }
                }
                lastProfit = 0;
            }
        }
        if (riverLines.length > 1) {
            if (startLineIndex == 0)
                maxProfit = innerProfit;
            else
                maxProfit = maxProfit + innerProfit;
            startLineIndex = startLineIndex + 1;
        } else {
            maxProfit = innerProfit;
        }

        if (startLineIndex < lineProcessorIndex + 1 && lineProcessorIndex != 0) {
            lineProcessed = false;
            return findMaxProfitAndBestOrders(riverLines, numberOfTrunks, river, 0, riverResult, riverIndex, caseNumber, result, lastProfit, startLineIndex
                    , lineProcessorIndex, lineProcessed, bestCombinationTrunks, maxProfit, 0);
        } else {
            result.append("Case:")
                    .append(caseNumber).append("\n").append("Max Profit:")
                    .append(maxProfit).append("\n").append("Order:")
                    .append(riverResult.toString()).append("\n");
        }
        return result;
    }

    /*get profits*/
    public int getProfit(int number, int maxProfit) {
        if (number == 1 || number == -1) {
            return maxProfit - 1;
        } else if (number == 2 || number == -2) {
            return maxProfit + 3;
        } else if (number == 3 || number == -3) {
            return maxProfit + 1;
        }
        return maxProfit;
    }

}