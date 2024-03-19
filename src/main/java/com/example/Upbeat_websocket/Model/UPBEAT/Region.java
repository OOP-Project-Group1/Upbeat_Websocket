package com.example.Upbeat_websocket.Model.UPBEAT;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Region {
    long deposit;
    Player owner;
    @Getter
    int row;
    @Getter
    int col;
    Region top;
    Region down;
    Region downRight;
    Region topRight;
    Region downLeft;
    Region topLeft;
    long maxDeposit;
    int hashcode=hashCode();
    public void getInvest(int amount){
        this.deposit+=amount;
        if(this.deposit>maxDeposit){
            this.deposit = maxDeposit;
        }
    }
    public int findMinimumMove(Region region){//region(target)


        return findMinimumMoveRe(getTop(),region,1);
        //the minimum moving distance from the current city center to the destination region
    }
    public int findMinimumMoveRe(Region region,Region target,int level){//region(target)
        if (checkCur(region,target)) return level;
        else {

            if (checkNext(getDownRight(), target,level,"dr")) return level;
            else if (checkNext(getDown(), target,level,"d")) return level;
            else if (checkNext(getDownLeft(), target,level,"dl")) return level;
            else if (checkNext(getTopLeft(), target,level,"tl")) return level;
            else if (checkNext(getTop(), target,level,"t")) return level;
            else if (checkNext(getTopRight(), target,level,"tr")) return level;
            else return findMinimumMoveRe(region.getTop(),target,level+1);
            //the minimum moving distance from the current city center to the destination region
        }
    }
    public boolean checkNext(Region region,Region target,int level,String state){
        if(region==null)return false;
        boolean p=false;
        if(level==1) p=checkCur(region,target);
        else if (level>1){
            p=checkCur(region,target);

            if(!p) {
                if(Objects.equals(state, "dr")) return checkNext(region.getDownRight(),target,level-1,"dr");
                else if(Objects.equals(state, "d")) return checkNext(region.getDown(),target,level-1,"d");
                else if(Objects.equals(state, "dl")) return checkNext(region.getDownLeft(),target,level-1,"dl");
                else if(Objects.equals(state, "tl")) return checkNext(region.getTopLeft(),target,level-1,"tl");
                else if(Objects.equals(state, "t")) return checkNext(region.getTop(),target,level-1,"t");
                else if(Objects.equals(state, "tr")) return checkNext(region.getTopRight(),target,level-1,"tr");
                else throw new RuntimeException("stat not exits");
            }


        }
        return p;

    }
    public boolean checkCur(Region region,Region target){
        if(region==null)return false;
        return region.hashCode()==target.hashCode();
    }

    public Region(int row , int col , Player owner , long deposit,int m , int n , long maxDeposit){
        this.row = row;
        this.col = col;
        this.owner = owner;
        this.deposit = deposit;
        this.maxDeposit = maxDeposit;

//        if(col-1<0) this.top = null;
//        else this.top = new Region(row,col-1,null,0 , m , n);
//
//        if(col+1>=m) this.down = null;
//        else this.down = new Region(row,col+1,null,0 , m , n);



    }
    public void print(){
        if(owner==null){
            System.out.println("Row : "+this.row +", Column : "+this.col+", Owner : empty"+" , Deposit : "+ this.deposit);
        }else{
            System.out.println("Row : "+this.row +", Column : "+this.col+", Owner : "+owner.name+" , Deposit : "+ this.deposit);
        }

//        if(top!=null){
//            System.out.println(" , Top : "+top.row+" , "+top.col);
//        }
//        if(top.top!=null){
//            System.out.println(" , Top top : "+top.top.row+" , "+top.top.col);
//        }
    }
    public void printAdj(){
        if(top!=null) System.out.println("Top "+top.row+top.col);
        if(topRight!=null) System.out.println("TopRight "+topRight.row+topRight.col);
        if(downRight!=null) System.out.println("DownRight "+downRight.row+downRight.col);
        if(down!=null) System.out.println("Down "+down.row+down.col);
        if(downLeft!=null) System.out.println("DownLeft "+downLeft.row+downLeft.col);
        if(topLeft!=null) System.out.println("TopLeft "+topLeft.row+topLeft.col);
    }

    void accruesInterest(long interest){
        this.deposit+=interest;
    }
    public Region getTop(){return top;}
    public Region getDown(){return down;}
    public Region getDownLeft(){return downLeft;}
    public Region getDownRight(){return downRight;}
    public Region getTopRight(){return topRight;}
    public Region getTopLeft(){return topLeft;}

    @Override
    public boolean equals(Object region)
    {

        // if both the object references are
        // referring to the same object.
        if(this == region)
            return true;

        // it checks if the argument is of the
        // type Geek by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if(region == null || region.getClass()!= this.getClass())
            return false;

        // type casting of the argument.
        Region geek = (Region) region;

        // comparing the state of argument with
        // the state of 'this' Object.
        //return (geek.nam.equals(this.name)  && geek.id == this.id);
        return false;
    }

    @Override
    public int hashCode() {
        return (row*1000)+(col*10);
    }



}