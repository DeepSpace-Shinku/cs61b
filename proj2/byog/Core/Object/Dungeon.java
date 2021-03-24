package byog.Core.Object;

import byog.Core.Index;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Dungeon extends RectangularObject{

    Room roomContained;
    Dungeon[] subDungeons = null;
    final int MAX_DUNGEON_LENGTH = 20, MIN_DUNGEON_LENGTH = 7, MIN_ROOM_LENGTH = 5;
    NumberGenerator random;
    boolean horizontalDivide;

    public Dungeon(RectangularSize s, TETile[][] w, Index i, NumberGenerator r)
    {
        size = s;
        objectIndex = i;
        world = w;
        random = r;
        //addTo(world, objectIndex);
        divide();
    }


    private void divide()
    {
        assert size.length() >= MIN_DUNGEON_LENGTH && size.width() >= MIN_DUNGEON_LENGTH: "Dungeon length is too small";
        if(shouldDivide()){
            subDungeons = new Dungeon[2];
            if(horizontalDivide()){
                horizontalDivide = true;
                int firstWidth = random.next(MIN_DUNGEON_LENGTH, size.width() - MIN_DUNGEON_LENGTH + 1);
                int secondWidth = size.width() - firstWidth;
                int middleVerticalIndex = bottomIndex() + firstWidth;
                RectangularSize firstSize= new RectangularSize(size.length(), firstWidth);
                RectangularSize secondSize = new RectangularSize(size.length(), secondWidth);
                Index firstIndex = objectIndex;
                Index secondIndex = new Index(objectIndex.horizontal(), middleVerticalIndex);
                subDungeons[0] = new Dungeon(firstSize, world, firstIndex, random);
                subDungeons[1] = new Dungeon(secondSize, world, secondIndex, random);
            }
            else{
                horizontalDivide = false;
                int firstLength = random.next(MIN_DUNGEON_LENGTH, size.length() - MIN_DUNGEON_LENGTH + 1);
                int secondLength = size.length() - firstLength;
                int middleHorizontalIndex = leftIndex() + firstLength;
                RectangularSize firstSize= new RectangularSize(firstLength, size.width());
                RectangularSize secondSize = new RectangularSize(secondLength, size.width());
                Index firstIndex = objectIndex;
                Index secondIndex = new Index(middleHorizontalIndex, objectIndex.vertical());
                subDungeons[0] = new Dungeon(firstSize, world, firstIndex, random);
                subDungeons[1] = new Dungeon(secondSize, world, secondIndex, random);
            }
        }
    }

    private boolean shouldDivide()
    {
        return size.length() >= MAX_DUNGEON_LENGTH || size.width() >= MAX_DUNGEON_LENGTH;
    }

    private boolean horizontalDivide()
    {
        if(size.length() >= MAX_DUNGEON_LENGTH && size.width() >= MAX_DUNGEON_LENGTH){
            return isHorizontalDivide();
        }else {
            if (size.length() >= MAX_DUNGEON_LENGTH) {
                return false;
            }
            return true;
        }
    }

    private boolean isHorizontalDivide()
    {
        if(random.next(0, 2) == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Basic dungeon means that the dungeon has no sub-dungeons.
     */
    public boolean isBasicDungeon()
    {
        return subDungeons == null;
    }

    private void addRooms()
    {
        assert isBasicDungeon(): "Room can only be added to basic dungeons.";
        RectangularSize roomSize = setRoomSize();
        Index roomIndex = setRoomIndex(roomSize);
        roomContained = new Room(roomSize, world, roomIndex);
    }

    private RectangularSize setRoomSize()
    {
        RectangularSize roomSize = new RectangularSize();
        roomSize.setLength(random.next(size.length()/2 + 2, size.length() - 1));
        roomSize.setWidth(random.next(size.width()/2 + 2, size.width() - 1));
        return roomSize;
    }

    private Index setRoomIndex(RectangularSize roomSize)
    {
        Index roomIndex = new Index();
        roomIndex.setHorizontal(random.next(this.leftIndex() + 1, this.rightIndex() - roomSize.length() + 1));
        roomIndex.setVertical(random.next(this.bottomIndex() + 1, this.topIndex() - roomSize.width() + 1));
        return roomIndex;
    }

    private void addHallways()
    {

        /**
        if(!isBasicDungeon()){
            Index middleIndex = new Index(leftIndex() + rightIndex() / 2, bottomIndex() + topIndex() / 2);
            if (horizontalDivide){
                Hallway hallway = new Hallway(false, world, hallwayIndex);
            }
            else{
                Hallway hallway = new Hallway(true, world, hallwayIndex);
            }
        }*/
        assert !isBasicDungeon(): "Hallways can only be added to non-basic dungeons.";
        Index middleIndex =  hallwayMiddleIndex();
        Hallway hallway = new Hallway(!horizontalDivide, world, middleIndex);
    }
    public Index hallwayMiddleIndex()
    {
        Index middleIndex = new Index();
        if (horizontalDivide){
            middleIndex.setHorizontal((leftIndex() + rightIndex()) / 2);
            middleIndex.setVertical((subDungeons[1].bottomIndex() - 1));
        } else{
            middleIndex.setHorizontal(subDungeons[1].leftIndex() - 1);
            middleIndex.setVertical((bottomIndex() + topIndex()) / 2);

        }
        return middleIndex;
    }
    public void addRoomsAndHallways()
    {
        if(isBasicDungeon())
        {
            addRooms();
        }else{
            for(Dungeon subDungeon: subDungeons) {
                subDungeon.addRoomsAndHallways();
            }
            addHallways();
        }
    }

    @Override
    public void addTo(TETile[][] world, Index begin_index)
    {
        Index currentIndex;
        TETile currentTile;
        for(int i : new Range(size.length())){
            for(int j : new Range(size.width())){
                currentIndex = new Index(begin_index.horizontal() + i,begin_index.vertical() + j);
                if (isOnEdge(i, j, size)){
                    currentTile = Tileset.FLOWER;
                }else{
                    currentTile = Tileset.NOTHING;
                }
                world[currentIndex.horizontal()][currentIndex.vertical()] = currentTile;
            }
        }
    }
}
