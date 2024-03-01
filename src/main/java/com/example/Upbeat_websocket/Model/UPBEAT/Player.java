package com.example.Upbeat_websocket.Model.UPBEAT;


import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

public class Player {
    @Setter
    public static int turn;

    public static Player[] instance = new Player[4];
    public static Player[] getInstance(){
        if(instance[0]==null){
            instance[0] = new Player("1");
            instance[1] = new Player("2");
            instance[2] = new Player("3");
            instance[3] = new Player("4");
        }
        return instance;
    }
    public static Player getInstanceP(int p){
        return instance[p];
    }

    public static
    String name;
    @Getter
    long budget;
    Region center;
    Region[][] territories;
    Path constructionPlan;
    Region location;
    public Player(String name){
        this.name = name;
    }
    public int locationGet(){
        String x =  String.valueOf(location.getRow());
        String y =  String.valueOf(location.getCol());
        String sum = x+y;
        return Integer.parseInt(sum);
    }
    public void revisePlan(int timeLimit){

    }

    public void devisePlan(int timeLimit){
        constructionPlan=null;
    }
    public void showStat(){
        System.out.println("Budget : " + budget);
        System.out.print("Location -> ");
        location.print();
        System.out.print("Center -> ");
        center.print();
        System.out.println("Territories -> ");
        for(Region[] row : territories){
            for(Region t : row){
                if(t!=null){
                    t.print();
                }
            }
        }
    }
    public void printLocation(){
        System.out.println("Budget : " + budget);
        System.out.print("Location -> ");
        location.print();
    }
    public void doConstructionPlan(){

    }

    /**
     * Once executed, the evaluation of the construction plan in that turn ends. This is similar to the
     * return statement in a procedure.
     */
    public void done(){
        //
    }

    /**
     * The invest command adds more deposits to the current region occupied by the city crew. The
     * total cost of an investment is i+1, where i is the investment amount. If the player does not have
     * enough budget to execute this command, the command acts like a no-op, but the player still
     * pays for the unit cost of the command. Otherwise, the player's deposit in the current region is
     * increased by i, but will not exceed the maximum deposit as specified in the configuration file. A
     * player may invest in a region belonging to no player as long as that region is adjacent to another
     * region belonging to the player.
     */
    public void invest(int investmentAmount){
        if(canInvest()){
            investmentAmount+=1;
            if(budget>=1) {
                if (this.budget < investmentAmount) {
                    this.budget -= 1;
                } else {
                    this.budget -= investmentAmount;

                    location.getInvest(investmentAmount - 1);
                }
                //set new owner
            }//throw no budget
        }
        //throw cannot invest
    }
    private boolean canInvest(){
        if(location.owner==this) return true;
        else if(location.owner!=null) return false;
        else return location.top.owner == this ||
                    location.topLeft.owner == this ||
                    location.topRight.owner == this ||
                    location.down.owner == this ||
                    location.downLeft.owner == this ||
                    location.downRight.owner == this;
    }

    /**
     * The collect command retrieves deposits from the current region occupied by the city crew.
     * Whenever this command is executed, the player's budget is decreased by one unit. If the
     * player does not have enough budget to execute this command, the evaluation of the
     * construction plan in that turn ends. If the specified collection amount is more than the deposit in
     * the current region, the command acts like a no-op, but the player still pays for the unit cost of
     * the command. Otherwise, the player's deposit in the current region is decreased by the
     * collection amount, which is then added to the player's available budget. If the deposit becomes
     * zero after the collection, the player loses the possession of that region.
     * @param amount
     */
    public void collect(int amount){
        if(this.budget>=1){
            budget-=1;
            if(this.location.owner==this) {
                if (amount <= location.deposit) {
                    location.deposit -= amount;
                    this.budget += amount;
                }
                if(this.location.deposit==0){
                    location.owner=null;
                }
            }//throw not occupy

        }//throw no budget | call done
    }
    /**
     * Find closet opponent region , The value of each location is relative to the city crew. The unit digit is between 1 and 6, and
     * indicates the direction of the location. The remaining digits indicate the distance from the city crew to that location.
     * @return number that identified closet opponent's region
     */
    public int opponent(){
        Region up=location;
        Region dow=location;
        Region upRight=location;
        Region upLeft=location;
        Region dowRight=location;
        Region dowLeft=location;
        int i=1;
        while (true) {
            if(up!=null){
                up=up.top;
                if(checkOpponent(up)) return i*10+1;
            }
            if(upRight!=null){
                upRight=upRight.topRight;
                if(checkOpponent(upRight)) return i*10+2;
            }
            if(dowRight!= null){
                dowRight=dowRight.downRight;
                if(checkOpponent(dowRight))return i*10+3;
            }
            if(dow!=null){
                dow=dow.down;
                if(checkOpponent(dow))return i*10+4;
            }
            if (dowLeft!=null) {
                dowLeft=dowLeft.downLeft;
                if(checkOpponent(dowLeft)) return i*10+5;
            }
            if (upLeft!=null){
                upLeft=upLeft.topLeft;
                if(checkOpponent(upLeft)) return i*10+6;
            }
            if(up == null && upRight==null && dowRight== null && dow==null && dowLeft==null && upLeft==null ){
                return 0;
            }
            i++;
        }
    }
    private boolean checkOpponent(Region region){
        if(region==null) return false;
        return region.owner!=null && !region.owner.name.equals(this.name);
    }

    /**
     * The nearby function looks for the opponent's region closest to the current location of the city
     * crew in a given direction. This command should return 100x+y, where x is the distance from the
     * city crew to that region as shown in the diagram above, and y is the number of digits in the
     * current deposit for that region, which can be helpful when planning attacks.
     * If no opponent owns a region in the given direction, the nearby function should return 0.
     */
    public int nearby(String direction){
        int x=1;
        long y;
        if(direction.equals("up")){
            Region current=location.top;
            while(current!=null){
                if(checkOpponent(current)){
                    y = current.deposit;
                    return (int) ((100*x)+y);
                }
                current=current.top;
                x++;
            }
        }
        else if(direction.equals("upright")){
            Region current=location.topRight;
            while(current!=null){
                if(checkOpponent(current)){
                    y = current.deposit;
                    return (int) ((100*x)+y);
                }
                current=current.topRight;
                x++;
            }
        }
        else if(direction.equals("downright")){
            Region current=location.downRight;
            while(current!=null){
                if(checkOpponent(current)){
                    y = current.deposit;
                    return (int) ((100*x)+y);
                }
                current=current.downRight;
                x++;
            }
        }
        else if(direction.equals("down")){
            Region current=location.down;
            while(current!=null){
                if(checkOpponent(current)){
                    y = current.deposit;
                    return (int) ((100*x)+y);
                }
                current=current.down;
                x++;
            }
        }
        else if(direction.equals("downleft")){
            Region current=location.downLeft;
            while(current!=null){
                if(checkOpponent(current)){
                    y = current.deposit;
                    return (int) ((100*x)+y);
                }
                current=current.downLeft;
                x++;
            }
        }
        else if(direction.equals("upleft")){
            Region current=location.topLeft;
            while(current!=null){
                if(checkOpponent(current)){
                    y = current.deposit;
                    return (int) ((100*x)+y);
                }
                current=current.topLeft;
                x++;
            }
        }
        return 0;
    }

    /**
     * This command relocates the city center to the current region. The cost to relocate the city
     * center is 5x+10, where x is the minimum moving distance from the current city center to the
     * destination region (regardless of the presence of any opponent's region in between). This cost
     * is deducted from the player's budget. If the player does not have enough budget to execute this
     * command, or if the current region does not belong to the player, the relocation fails. Once
     * executed (regardless of the outcome), the evaluation of the construction plan in that turn ends.
     */
    public void relocate(){
        if(location!=center){
            int minimumMove = location.findMinimumMove(center);
            int cost = 5*minimumMove+10;
            if(budget>=cost){
                budget-=cost;
                center=location;
                //end call done
            }else{
                //throw no budgett
            }
        }//throw already center

    }

    /**
     * The move command moves the city crew one unit in the specified direction. If the destination
     * region belongs to another opponent, the command acts like a no-op. Whenever this command
     * is executed (regardless of validity), the player's budget is decreased by one unit. If the player
     * does not have enough budget to execute this command, the evaluation of the construction plan
     * in that turn ends.
     */
    public void move(String direction){
        Region destination;
        if(budget>=1) {
            budget -= 1;
            switch (direction) {
                case "up" -> destination = location.top;
                case "upright" -> destination = location.topRight;
                case "downright" -> destination = location.downRight;
                case "down" -> destination = location.down;
                case "downleft" -> destination = location.downLeft;
                case "upleft" -> destination = location.topLeft;
                default -> destination = null;

            }
            if(destination==null){
                //throw no region to move
            }else if(destination.owner==null || destination.owner==this){
                location=destination;
            }else{
                //thorw enemy area
            }

        }
        //throw no money
        done();
    }
    /**
     * The shoot command attempts to attack a region located one unit away from the city crew in the
     * specified direction.
     * Each attack is given an expenditure, i.e., how much the player would like to spend in that attack.
     * This will be deducted from the budget. Each attack also has a fixed budget cost of one unit.
     * That is, the total cost of an attack is x+1, where x is the given expenditure. If the player does
     * not have enough budget to execute this command, the command acts like a no-op. Otherwise,
     * the opponent's deposit in the target region will be decreased no more than the expenditure.
     * Specifically, if the expenditure is x and the opponent's deposit is d, then after the attack, the
     * deposit will be max(0, d-x). If the deposit becomes less than one, the opponent loses
     * ownership of that region. In this case, the target region does not automatically become the
     * current player's region; the player needs to move into that region and invest in order to claim it.
     * If the target region is unoccupied, the player still pays the cost of the attack, but the attack itself
     * will have no effects otherwise.
     * If the target region belongs to the current player (i.e., a player is attacking its own region), the
     * command has the same effect as the normal scenario, i.e., it acts as self destruction. Be careful
     * who you shoot at!
     * Finally, if the target region is a city center, and the attack reduces its deposit to zero, the
     * attacked player loses the game. The other regions that belong to the losing player will be
     * ownerless, but the deposit will remain there for the remaining players to discover and claim it.
     * Ownerless deposits will not accrue interests.
     */
    public void shoot(String direction, int cost){
        Region destination;
        if(budget>=cost+1){
            budget-=cost+1;
            switch (direction) {
                case "up" -> destination = location.top;
                case "upright" -> destination = location.topRight;
                case "downright" -> destination = location.downRight;
                case "down" -> destination = location.down;
                case "downleft" -> destination = location.downLeft;
                case "upleft" -> destination = location.topLeft;
                default -> destination = null;
            }
            if(destination!=null){
                if(destination.owner==null){

                }else{
                    destination.deposit-=cost;
                    if(destination.deposit<1){
                        if(destination.owner.center==destination){
                            destination.owner.loseGame();
                        }
                        destination.deposit=0;
                        destination.owner=null;
                    }
                }


            }



        }else{
            //throw no money (no op)
        }
    }
    private void loseGame(){}



}