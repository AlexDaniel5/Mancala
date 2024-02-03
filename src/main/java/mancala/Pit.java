    package mancala;

    import java.io.Serializable;

    public class Pit implements Countable, Serializable{
        private static final long serialVersionUID = -8581857526535546531L;
        private int stoneCount;

        //Constructor method
        public Pit() {
            stoneCount = 0;
        }

        @Override
        public void addStone() {
            stoneCount++;
        }

        @Override
        public void addStones(final int amount) {
            stoneCount += amount;
        }

        //Accessor method
        @Override
        public int getStoneCount() {
            return stoneCount;
        }

        //Reset stones to 0 and return the number of stones that were in the pit
        @Override
        public int removeStones() {
            final int removedStones = stoneCount;
            stoneCount = 0;
            return removedStones;
        }
    }