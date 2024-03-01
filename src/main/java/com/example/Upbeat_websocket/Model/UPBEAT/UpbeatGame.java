package com.example.Upbeat_websocket.Model.UPBEAT;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Getter
public class UpbeatGame {
    int m;
    int n;
    long planMin;
    long planSec;
    long budget;
    long centerDep;
    long revMin;
    long revSec;
    long revCost;
    long maxDep;
    long interestPct;
    private Player[] players;
    public Region[][] map;
    private int turn;
    private Player firstPlayer;
    public UpbeatGame(Path file , Player[] players){
        setup(file,players);
    }
    //    private static UpbeatGame instance;
//    public static UpbeatGame getInstance(){
//        if(instance==null){
//            instance = new UpbeatGame(file, players);
//        }
//        return instance;
//    }
    public Region getRegion(int i,int j){
        return map[i][j];
    }

    /**
     * read configuration
     * @param file
     * @return Map shows variable and value these file define
     * checks:
     *      -empty file
     *      -file not found
     *      -not configuration file
     */
    private Map<String, Long> readConfiguration(Path file) {
        //String[] configuration = new String[20];
        Map<String, Long> configuration = new HashMap<String,Long>();
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(file , charset)){
            String line;
            while((line = reader.readLine()) != null) {
                String[] splited = line.split("=");
                //if there is no = at all -> throw exception
                configuration.put(splited[0], Long.valueOf(splited[1]));
            }
        }catch (FileNotFoundException | NullPointerException e){
            System.out.println("File not found , or empty file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configuration;
    }

    /**
     * assign the values of variable from configuration file
     * create map , call setUpPlayer
     * and start the game
     * @param file
     * @param players
     */
    public void setup(Path file , Player[] players) {
        try {
            Map<String, Long> configuration = readConfiguration(file);
            for (Map.Entry<String, Long> line : configuration.entrySet()) {
                String key = line.getKey();
                long value = line.getValue();
                switch (key) {
                    case "m" -> this.m = (int) value;
                    case "n" -> this.n = (int) value;
                    case "init_plan_min" -> this.planMin = value;
                    case "init_plan_sec" -> this.planSec = value;
                    case "init_budget" -> this.budget = value;
                    case "init_center_dep" -> this.centerDep = value;
                    case "plan_rev_min" -> this.revMin = value;
                    case "plan_rev_sec" -> this.revSec = value;
                    case "rev_cost" -> this.revCost = value;
                    case "max_dep" -> this.maxDep = value;
                    case "interest_pct" -> this.interestPct = value;
                }
            }

            map = new Region[m][n];
            for(int i = 0 ; i<m ; i++){
                for(int j = 0 ; j<n ; j++){
                    map[i][j] = new Region(i,j,null,0,m,n,maxDep);
                }
            }
            for(int i = 0 ; i<m ; i++){
                for(int j = 0 ; j<n ; j++){
                    if(i-1>=0) map[i][j].top = map[i-1][j];
                    if(i+1<m) map[i][j].down = map[i+1][j];


                    //if col is odd , left up row is the same
                    //if col is even , left up will -1 row
                    if(j%2==1 && j-1>=0 && i-1>=0 ) map[i][j].topLeft = map[i-1][j-1];
                    if(j%2==0 && j-1>=0) map[i][j].topLeft = map[i][j-1];

                    //if col is even , left down row is the same
                    //if col is odd , left down will +1 row
                    if(i+1<m && j%2==0 && j-1>=0) map[i][j].downLeft = map[i+1][j-1];
                    if(j-1>=0 && j%2==1) map[i][j].downLeft = map[i][j-1];

                    //if col is odd , right up row is the same
                    //if col is even , right up will -1 row
                    if(j%2==1 && j+1<n && i-1>=0) map[i][j].topRight = map[i-1][j+1];
                    if(j%2==0 && j+1<n ) map[i][j].topRight = map[i][j+1];

                    //if col is even , right down row is the same
                    //if col is odd , right down will +1 row
                    if(i+1<m && j%2==0 && j+1<n) map[i][j].downRight = map[i+1][j+1];
                    if(j+1<n && j%2==1) map[i][j].downRight = map[i][j+1];
                }
            }


            setUpPlayer(players);
//                findFirstPlayer(players);
//                turnRunner(players);

        }catch (NullPointerException e){
            System.out.println("No players");
            throw e;
        }

    }

    /**
     * set budget and center for every player and players choose initial construction plan
     * @param players
     * effects : player get budget , center and territories
     */
    private void setUpPlayer(Player[] players){
        this.players = players;
        for(Player player : players){
            player.budget=this.budget;
            player.center=randomCenter();
            player.center.owner=player;
            player.territories = new Region[this.m][this.n];
            player.territories[player.center.row][player.center.col]=player.center;
            player.devisePlan((int) (planMin*60+planSec)); //golf
            player.location=player.center;
        }
    }

    /**
     * random center for player and set initial deposit in that region
     * @return region that no one own
     * effects : change deposit of that region
     */
    private Region randomCenter(){
        Random rand = new Random();
        if(m>0&&n>0){
            int r = this.m-1;
            int c = this.n-1;
            int row = rand.nextInt(r);
            int col = rand.nextInt(c);
            for(int i=0;i<m*n;i++){
                if(this.map[row][col].owner == null) {
                    this.map[row][col].deposit = centerDep;
                    return this.map[row][col];
                }
            }
            throw new NullPointerException("No region available!");
        }else{
            throw new IllegalArgumentException("Row and Column of Map is negative");
        }


    }

    /**
     * Find first player to play
     * @param players
     */
    int findFirstPlayer(Player[] players){
        Random rand = new Random();
        int first = rand.nextInt(players.length-1);
        firstPlayer = players[first];
        return first;
    }

    /**
     * run the turn of each player until game finish
     * @param players
     */
    void turnRunner(Player[] players,int number){
        turn(firstPlayer);
        while(number!=0){ //checkWin!
            number = (number+1) % players.length;
            turn(players[number]);

        }
    }
    private boolean checkWin(){
//        if(){
//            return true;
//        }
        return false;
    }

    /**
     * First each region that belong to this player get interest
     * and then player has time to revise plan
     * after that construction plan start
     * @param player
     */
    private void turn(Player player){
        //first accrues interest
        player.location = player.center;
        for(Region[] row : map){
            for(Region t : row){
                if(t.owner!=null && t.owner==player){
                    t.accruesInterest(interestPct); //non
                }
            }
        }
        player.revisePlan((int) (revMin*60 + revSec)); //golf
        player.doConstructionPlan();
    }

}
