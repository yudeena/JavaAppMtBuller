/**
 * Entry point of the program.
 * Starts the Mt Buller Resort management system.
 */

public class MtBullerAdmin {

    public static void main(String[] args) {
        MtBullerResort resort = new MtBullerResort();
        resort.populateLists();
        resort.createAndShowGUI();
    }
}

