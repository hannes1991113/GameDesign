//16er Kosten 4 Mana, 24er Kosten 8 Mana
import java.util.ArrayList;

public class CardGenerator {
    // only optimized dices implemented (best dice on action)
    public static void main(String[] args) {
        CardGen cg = new CardGen();
        // card costs: 16, 24
        // generate specific cards based on actions with or without balancecost
        // cg.genAllCards(0, 1, 1,0,0, 0, 0); //
        // 1er_2er_3er_4er_5er_6er_shoot0/1
        // cg.genCard(0,1,0,2,0,0,0, 16);//
        // 1er_2er_3er_4er_5er_6er_shoot0/1_cost___noPrintIfNotFound

        cg.printCards(16);
        System.out.println();
        cg.printCards(24);
        System.out.println();
        /**/
        for (int i = 0; i < 100; i++) { // Print how many cards to which cost
                                        // exist
            System.out.print(i + ":" + cg.countCards(i) + " |");
            if ((i + 1) % 8 == 0)
                System.out.println();
        }/**/

    } // END MAIN
} // END PUBLIC CLASS

class CardGen {
    private ArrayList<Action> actionsPool = new ArrayList<>();

    private ArrayList<String[]> actions = new ArrayList<String[]>();

    private int actionCount1er;

    private int actionCount2er;

    private int actionCount3er;

    private int actionCount4er;

    private int actionCount5er;

    private int actionCount6er;

    private int diceForShootAction; // Shoot Action W+

    public CardGen() { // initialize actionsPool
        // Fähigkeiten,_shoot_mit_diceForShootAction_behandeln
        boolean newSetting = true;
        if (newSetting) { /* New_Setting */
            diceForShootAction = 5;
            actions.add(new String[] {}); // 0er
            actions.add(new String[] { "1+PRAY(1)", "1+CURSE(2)" , "2+Repair", "2+AID", "3+Sabotage" }); // 1er
            actions.add(new String[] { }); // 2er
            actions.add(new String[] { "2+FIGHT", "3+FIGHT_AW", "4+PLUNDER/DRAW", "3+FIGHT_VW" , "3+Haste"}); // 3er
            actions.add(new String[] { }); // 4er
            actions.add(new String[] {}); // 5er
            actions.add(new String[] {}); // 6er
        } else {/* Old_Setting */
            diceForShootAction = 4;
            actions.add(new String[] {}); // 0er
            actions.add(new String[] { "PRAY(2)", "CURSE(2)" }); // 1er
            actions.add(new String[] { "FIGHT" }); // 2er
            actions.add(new String[] { "FIGHT_AW", "SABOTAGE", "DRAW", "REPAIR" }); // 3er
            actions.add(new String[] { "FIGHT_VW", "PLUNDER", "HASTE" }); // 4er
            actions.add(new String[] {}); // 5er
            actions.add(new String[] {}); // 6er
        }
        for (int actionDice = 0; actionDice < 7; actionDice++) {
            actionsPool.add(new Action(actionDice));
            if (actions.get(actionDice).length != 0 || actionDice == diceForShootAction)
                System.out.print(actionDice + "+: ");
            for (int i = 0; i < actions.get(actionDice).length; ++i)
                System.out.print(actions.get(actionDice)[i] + ", ");
            if (diceForShootAction == actionDice)
                System.out.print("ShootX, ShootAW, ShootAW+X");
            if (0 != actions.get(actionDice).length || actionDice == diceForShootAction)
                System.out.println();
        }
        actionCount1er = actions.get(1).length;
        actionCount2er = actions.get(2).length;
        actionCount3er = actions.get(3).length;
        actionCount4er = actions.get(4).length;
        actionCount5er = actions.get(5).length;
        actionCount6er = actions.get(6).length;
        System.out.println();
    }

    public int countCards(int cardsCost) {
        return (generateCards(cardsCost, false));
    }

    public int printCards(int cardsCost) {
        return (generateCards(cardsCost, true));
    }

    public int genAllCards(int num1er, int num2er, int num3er, int num4er, int num5er, int num6er, int shootAbility) {
        int cardNum = 0;
        for (int i = 0; i < 60; i++) {
            cardNum += genCard(num1er, num2er, num3er, num4er, num5er, num6er, shootAbility, i);
        }
        return cardNum;
    }

    public int genCard(int num1er, int num2er, int num3er, int num4er, int num5er, int num6er, int shootAbility, int cardCost1) {
        int cardCount = 0;
        int numOfActions = 0;
        ArrayList<Action> listOfActions;
        // Fighting Values
        for (int attackValue = 0; attackValue < 5; attackValue++) { // ATK€0-4
            for (int defenseValue = 1; defenseValue < 6; defenseValue++) { // DEF1-4
                for (int shootX = 0; shootX < 5; shootX++) {
                    if (shootAbility == 0 && shootX > 0)
                        continue;
                    listOfActions = new ArrayList<>();
                    for (int i = 0; i < num1er; i++) {
                        listOfActions.add(actionsPool.get(1));
                    }
                    for (int i = 0; i < num2er; i++) {
                        listOfActions.add(actionsPool.get(2));
                    }
                    for (int i = 0; i < num3er; i++) {
                        listOfActions.add(actionsPool.get(3));
                    }
                    for (int i = 0; i < num4er; i++) {
                        listOfActions.add(actionsPool.get(4));
                    }
                    for (int i = 0; i < num5er; i++) {
                        listOfActions.add(actionsPool.get(5));
                    }
                    for (int i = 0; i < num6er; i++) {
                        listOfActions.add(actionsPool.get(6));
                    }
                    for (int s = 0; s < shootAbility; s++) {
                        listOfActions.add(new Action(diceForShootAction, shootX, attackValue));
                    }
                    Card test = new Card(attackValue, defenseValue, listOfActions);

                    //numOfActions = num1er + num2er + num3er + num4er + num5er + num6er;
                    if (test.balanceWert() == cardCost1) {
                        if (true)
                            System.out.println(test.toString());
                        cardCount++;
                    }
                }
            }
        }

        return cardCount;
    }

    private int generateCards(int cardsCost, boolean print) {
        int numOfActions = 0;
        int cardCount = 0;
        ArrayList<Action> listOfActions;
        for (int attackValue = 0; attackValue < 5; attackValue++) { // ATK€0-4
            for (int defenseValue = 1; defenseValue < 6; defenseValue++) { // DEF€1-4
                for (int ac1 = 0; ac1 < actionCount1er + 1; ac1++) {
                    for (int ac2 = 0; ac2 < actionCount2er + 1; ac2++) {
                        for (int ac3 = 0; ac3 < actionCount3er + 1; ac3++) {
                            for (int ac4 = 0; ac4 < actionCount4er + 1; ac4++) {
                                for (int ac5 = 0; ac5 < actionCount5er + 1; ac5++) {
                                    for (int ac6 = 0; ac6 < actionCount6er + 1; ac6++) {
                                        for (int shootActionCount = 0; shootActionCount < 2; shootActionCount++) {// 1ShootAction
                                            for (int shootX = 0; shootX < 5; shootX++) {
                                                if (shootActionCount == 0 && shootX > 0)
                                                    continue;
                                                // FILL_In_Actions
                                                listOfActions = new ArrayList<>();
                                                for (int i = 0; i < ac1; i++)
                                                    listOfActions.add(actionsPool.get(1));
                                                for (int i = 0; i < ac2; i++)
                                                    listOfActions.add(actionsPool.get(2));
                                                for (int i = 0; i < ac3; i++)
                                                    listOfActions.add(actionsPool.get(3));
                                                for (int i = 0; i < ac4; i++)
                                                    listOfActions.add(actionsPool.get(4));
                                                for (int i = 0; i < ac5; i++)
                                                    listOfActions.add(actionsPool.get(5));
                                                for (int i = 0; i < ac6; i++)
                                                    listOfActions.add(actionsPool.get(6));
                                                for (int s = 0; s < shootActionCount; s++)
                                                    listOfActions.add(new Action(diceForShootAction, shootX, attackValue));
                                                Card test = new Card(attackValue, defenseValue, listOfActions);
                                                
                                                //numOfActions = ac1 + ac2 + ac3 + ac4 + ac5 + ac6;
                                                if (test.balanceWert() == cardsCost) {
                                                    if (print)
                                                        System.out.println(test.toString());
                                                    cardCount++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return cardCount;
    }
}

class Card {
    private int AW;

    private int VW;

    private int COST;

    private ArrayList<Action> actions;

    public Card(int a, int v, ArrayList<Action> ac) {
        AW = a;
        VW = v;
        actions = ac;
        COST = balanceWert();
    }

    public String toString() {
        return " " + AW + "/" + VW + " | Cost: " + COST + " | " + actions.toString();
    }

    public int balanceWert() {
        return kampfBalance() + aktionsBalance() + actions.size()*2 + (actions.size()>2?1:0);//Math.round((float)(Math.pow(actions.size(),1.)));
    }

    private int kampfBalance() {
        double exponent = 1.85;
        double faktor = 1;
        
        //return (int) (Math.ceil(Math.pow(VW -1, exponent)*faktor) + Math.ceil(Math.pow(AW, exponent)*faktor));
        int awert = 0; int vwert = 0;
        if(AW == 0) awert = 0;
        if(AW == 1) awert = 1;
        if(AW == 2) awert = 3;
        if(AW == 3) awert = 6;
        if(AW == 4) awert = 10;
        

        if(VW == 1) vwert = 0;
        if(VW == 2) vwert = 1;
        if(VW == 3) vwert = 3;
        if(VW == 4) vwert = 6;
        if(VW == 5) vwert = 10;
        
        return awert + vwert;
        
    }

    private int aktionsBalance() {
        int awBalance = 0;
        for (int i = 0; i < actions.size(); i++)
            awBalance += actions.get(i).balance();
        return awBalance;
    }
}

class Action {
    public int dice;

    public int modifier;

    public int aw = -1;

    public int x = 0;

    public Action(int d, int x, int aw) {
        dice = d;
        modifier = x + 2 * aw;
        this.aw = aw;
        this.x = x;
    }

    public Action(int d) {
        dice = d;
        modifier = 0;
    }

    public String toString() {
        String cardValue = "";// + value;
        String xs = "";
        if (aw > -1)
            cardValue = "AW";
        if (x > 0)
            xs = "_" + x + "dmg";
        return dice + "+" + cardValue + xs;
    }

    public int balance() {
        if (dice == 1)
            return 1; // whatever
        if (dice == 2)
            return 3 + modifier;
        if (dice == 3)
            return 2 + modifier;
        if (dice == 4)
            return 5 + modifier;
        if (dice == 5)
            return 6 + modifier;
        if (dice == 6)
            return 7 + modifier;
        return 9999; // if dice==0
    }
}