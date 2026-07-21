package chickeninvaders;

/*
* This class defines the available planes in the Store.
* It provides each plane's cost, speed, shot delay, lives, and special ability.
*/

public class PlaneType {

    public static final String DEFAULT = "Default";

    public static final String FAST = "Fast";

    public static final String HEAVY = "Heavy";

    public static final String SNIPER = "Sniper";

    public static String[] getAllPlanes(){

        return new String[]{
                DEFAULT,
                FAST,
                HEAVY,
                SNIPER
        };
    }

    public static int getCost(String planeName){

        if(planeName.equals(FAST)){
            return 5000;
        }
        else if(planeName.equals(HEAVY)){
            return 8000;
        }
        else if(planeName.equals(SNIPER)){
            return 10000;
        }

        return 0;
    }

    public static int getSpeed(String planeName){

        if(planeName.equals(FAST)){
            return 7;
        }
        else if(planeName.equals(HEAVY)){
            return 4;
        }
        else if(planeName.equals(SNIPER)){
            return 5;
        }

        return 5;
    }

    public static int getShotDelay(String planeName){

        if(planeName.equals(FAST)){
            return 250;
        }
        else if(planeName.equals(HEAVY)){
            return 200;
        }
        else if(planeName.equals(SNIPER)){
            return 150;
        }

        return 300;
    }

    public static int getInitialLives(String planeName){

        if(planeName.equals(HEAVY)){
            return 5;
        }

        return 3;
    }

    public static boolean isSniper(String planeName){

        return planeName.equals(SNIPER);
    }

    public static boolean isValidPlane(String planeName){

        if(planeName == null){
            return false;
        }

        return planeName.equals(DEFAULT)
                || planeName.equals(FAST)
                || planeName.equals(HEAVY)
                || planeName.equals(SNIPER);
    }

    public static String getInfoText(String planeName){

        String specialFeature = "-";

        if(planeName.equals(SNIPER)){
            specialFeature = "Double damage to bosses";
        }

        return planeName
                + " | Cost: " + getCost(planeName)
                + " | Speed: " + getSpeed(planeName)
                + " | Shot Delay: " + getShotDelay(planeName) + " ms"
                + " | Lives: " + getInitialLives(planeName)
                + " | Special: " + specialFeature;
    }
}