package com.example.Upbeat_websocket.Model.UPBEAT;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class UpbeatGameTest {
    @Test
    //Test setup ( read configuration , set budget,center,territories to players)
    public void setupTest() {

        Path file = Paths.get("configuration_file/Configuration_file.txt");
        Player yai = new Player("Yai");
        Player non = new Player("Non");
        Player golf = new Player("Golf");
        Player[] players = new Player[3];
        players[0]=yai;
        players[1]=non;
        players[2]=golf;
        UpbeatGame a = new UpbeatGame(file,players);
        assertEquals(8,a.m);
        assertEquals(8,a.n);
        assertEquals(5,a.planMin);
        assertEquals(0,a.planSec);
        assertEquals(10000,a.budget);
        assertEquals(100,a.centerDep);
        assertEquals(30,a.revMin);
        assertEquals(0,a.revSec);
        assertEquals(100,a.revCost);
        assertEquals(5000,a.maxDep);
        assertEquals(5,a.interestPct);
        for(Player p : players){
                p.showStat();
        }
    }
    @Test
    public void turnRunnerTest(){
        Path file = Paths.get("configuration_file/Configuration_file.txt");
        Player yai = new Player("Yai");
        Player non = new Player("Non");
        Player golf = new Player("Golf");
        Player[] players = new Player[3];
        players[0]=yai;
        players[1]=non;
        players[2]=golf;
        UpbeatGame a = new UpbeatGame(file,players);
        for(Player p : players){
            p.showStat();
        }

        a.turnRunner(players, a.findFirstPlayer(players));
        for(Player p : players){
            p.showStat();
        }
        //System.out.println(yai.opponent());
    }
    @Test
    public void mapTest(){
        Path file = Paths.get("configuration_file/Configuration_file.txt");
        Player yai = new Player("Yai");
        Player[] players = new Player[1];
        players[0]=yai;
        UpbeatGame a = new UpbeatGame(file,players);
        Region even = a.getRegion(1,1);
        even.printAdj();

        Region odd = a.getRegion(2,2);
        odd.printAdj();

    }
    @Test
    public void minDistance(){
        Path file = Paths.get("configuration_file/Configuration_file.txt");
        Player yai = new Player("Yai");
        Player[] players = new Player[1];
        players[0]=yai;
        UpbeatGame a = new UpbeatGame(file,players);
        Region target=a.map[1][5];
        Region region=a.map[2][2];

        System.out.println(region.hashCode());
        System.out.println(target.hashCode());
        System.out.println(region.findMinimumMove(target));

    }
    @Test
    public void opponentTest(){
        Path file = Paths.get("configuration_file/Configuration_file.txt");
        Player yai = new Player("Yai");
        Player non = new Player("Non");
        Player golf = new Player("Golf");
        Player[] players = new Player[3];
        players[0]=yai;
        players[1]=non;
        players[2]=golf;
        UpbeatGame a = new UpbeatGame(file,players);
        for(Player p : players){
            p.showStat();
        }
        System.out.println(yai.opponent());
    }
    @Test
    public void investTest(){
        Path file = Paths.get("configuration_file/Configuration_file.txt");
        Player yai = new Player("Yai");
        Player[] players = new Player[1];
        players[0]=yai;
        UpbeatGame a = new UpbeatGame(file,players);
        for(Player p : players){
            p.showStat();
        }
        System.out.println("Before invest");
        yai.location.print();
        yai.invest(500);
        System.out.println("After Invest");
        assertEquals(yai.location.deposit,600);
        assertEquals(yai.budget,10000-501);
        System.out.println("Invest more than budget");
        yai.invest(1000000);
        assertEquals(yai.budget,10000-501-1);
        System.out.println("Invest max deposit (5000)");
        yai.invest(5000);
        assertEquals(yai.budget,10000-501-1-5001);
        assertEquals(yai.location.deposit,5000);
        //Invest in empty region
        //invest in adj region
        //invest in opponent region
        //no budget
    }
    @Test
    public void collectTest(){
        //collect
        //not occupy
        //more than deposit
        //deposit = 0
    }
    @Test
    public void relocateTest(){
        //relocate
        //no budget
        //check cost
        //check cost if opp on the way
    }
    @Test
    public void nearbyTest(){
        //in range 1 2 3
        //not in range
    }
    @Test
    public void moveTest(){
        //my area
        //empty
        //dead end
        //enemy
        //no budget
    }
    @Test
    public void shootTest(){
        //attack enemy
        //attack your own
        //attack empty
        //enemy lose
        //attack null
    }



}