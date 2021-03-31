package GoogleFooBar.BringingAGunToALaserFight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Solution {

    private static HashSet<Double> shotAnglesGuard = new HashSet<>();
    private static HashMap<Double, ArrayList<int[]>> shotAnglesYourselfMap = new HashMap<>();

    private static int numOfWays = 0;

    public static int solution(int[] dimensions, int[] yourPosition, int[] trainerPosition, int distance) {

        int numOfXReflections = distance / dimensions[0] + 1;
        int numOfYReflections = distance / dimensions[1] + 1;

        ArrayList<int[]> allYourReflectedCoordinates = new ArrayList<>();
        ArrayList<int[]> allTrainerReflectedCoordinates = new ArrayList<>();

        generateAllReflectedCoordinates(allYourReflectedCoordinates, allTrainerReflectedCoordinates, numOfXReflections, numOfYReflections, dimensions, yourPosition, trainerPosition);

        double originalDistance = calculateDistanceBetweenTwoPoints(yourPosition, trainerPosition);

        if (originalDistance <= distance) {
            numOfWays++;
            shotAnglesGuard.add(Math.atan2(trainerPosition[1] - yourPosition[1], trainerPosition[0] - yourPosition[0]));
        } else {
            return 0;
        }

        int index = 0;
        while (index < allYourReflectedCoordinates.size()) {

            double currentDistance = calculateDistanceBetweenTwoPoints(yourPosition, allTrainerReflectedCoordinates.get(index));

            if (currentDistance <= distance) {
                Double trainerShotAngle = Math.atan2(allTrainerReflectedCoordinates.get(index)[1] - yourPosition[1], allTrainerReflectedCoordinates.get(index)[0] - yourPosition[0]);

                if (!shotAnglesGuard.contains(trainerShotAngle)) {
                    boolean laserHitsYourself = shotAnglesYourselfMap.containsKey(trainerShotAngle);
                    boolean[] laserHitsTrainerBeforeYou = {true};

                    if (laserHitsYourself) {
                        shotAnglesYourselfMap.get(trainerShotAngle).forEach(arr -> {
                            double dist = calculateDistanceBetweenTwoPoints(yourPosition, arr);

                            if (dist < currentDistance) {
                                laserHitsTrainerBeforeYou[0] = false;
                            }
                        });
                    }

                    if (laserHitsTrainerBeforeYou[0]) {
                        numOfWays++;
                        shotAnglesGuard.add(trainerShotAngle);
                    }
                }
            }
            index++;
        }
        return numOfWays;
    }

    private static void generateAllReflectedCoordinates(ArrayList<int[]> allYourReflectedCoordinates, ArrayList<int[]> allTrainerReflectedCoordinates, int numOfXReflections, int numOfYReflections, int[] dimensions, int[] yourPosition, int[] trainerPosition) {
        for (int currXReflection = -numOfXReflections; currXReflection <= numOfXReflections; currXReflection++) {

            for (int currYReflection = -numOfYReflections; currYReflection <= numOfYReflections; currYReflection++) {

                if (currXReflection == 0 && currYReflection == 0) {
                    continue;
                }

                int[] your_reflected_position = getReflectedPosition(dimensions, yourPosition, currXReflection, currYReflection);
                int[] trainer_reflected_position = getReflectedPosition(dimensions, trainerPosition, currXReflection, currYReflection);

                Double angle = Math.atan2(your_reflected_position[1] - yourPosition[1], your_reflected_position[0] - yourPosition[0]);

                if (shotAnglesYourselfMap.containsKey(angle)) {
                    shotAnglesYourselfMap.get(angle).add(your_reflected_position);
                } else {
                    ArrayList<int[]> temp = new ArrayList<>();
                    temp.add(your_reflected_position);
                    shotAnglesYourselfMap.put(angle, temp);
                }
                allYourReflectedCoordinates.add(your_reflected_position);
                allTrainerReflectedCoordinates.add(trainer_reflected_position);
            }
        }
    }

    private static int[] getReflectedPosition(int[] dimensions, int[] position, int currXReflection, int currYReflection) {
        int xPos = calculateReflectedPosition(dimensions[0], position[0], currXReflection);
        int yPos = calculateReflectedPosition(dimensions[1], position[1], currYReflection);

        return new int[]{xPos, yPos};
    }

    private static int calculateReflectedPosition(int dimension, int position, int numOfReflections) {

        if (numOfReflections < 0) {
            int diff = position;

            if (numOfReflections % 2 == 0) {
                diff = dimension - position;
            }

            position = -((Math.abs(numOfReflections) - 1) * dimension + diff);
        }

        if (numOfReflections > 0) {
            int diff = position;

            if (numOfReflections % 2 == 0) {
                diff = dimension - position;
            }

            position = numOfReflections * dimension + (dimension - diff);
        }

        return position;
    }

    private static double calculateDistanceBetweenTwoPoints(int[] your_position, int[] trainer_position) {
        double x_diff_squared = Math.pow(Math.abs(your_position[0] - trainer_position[0]), 2);
        double y_diff_squared = Math.pow(Math.abs(your_position[1] - trainer_position[1]), 2);

        return Math.sqrt(x_diff_squared + y_diff_squared);
    }

    public static void main(String[] args) {
        int[] dimension = {3, 3};
        int[] your_pos = {0, 0};
        int[] trainer_pos = {3, 0};
        int dist = 3;
        System.out.println(solution(dimension, your_pos, trainer_pos, dist));
    }
}
