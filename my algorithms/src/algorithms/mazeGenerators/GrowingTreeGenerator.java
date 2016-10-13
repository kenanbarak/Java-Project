package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;

public class GrowingTreeGenerator  extends CommonMaze3dGenerator
{
	private Maze3d maze3d;
	private CommonPop commonPop;

	//constructor
	public GrowingTreeGenerator(CommonPop CommonPop)
	{
		this.commonPop = CommonPop;
	}


	@Override
	public Maze3d generate(int x_size, int y_size, int z_size)
	{
		int check=0;

		maze3d = new Maze3d(x_size*2+1, y_size*2+1, z_size*2+1);

		//put 1 in every cell
		maze3d.buildWalls();

		//pick a random start position
		maze3d.GetRandomStartPosition();
		Position start = maze3d.getStartPosition();

		//pick a random goal position
		maze3d.GetRandomGoalPosition();
		Position goal = maze3d.getGoalPosition();

		//initialize list of cells
		ArrayList<Position> c = new ArrayList<Position>();

		//add the start position to the list and put there 0
		c.add(start);
		maze3d.setPostionValue(start.getX(),start.getY(),start.getZ(),0);


		Position pos = new Position(maze3d.getStartPosition());
		do {
			pos = new Position (commonPop.selectPop(c));
			Integer [] rnd= RandomDirections();
			boolean breakLoop = false;

			for (int i = 0; i < rnd.length; i++)
			{
				if(breakLoop==true)
					break;

				check=0;
				switch(rnd[i])
				{
				case 1: //backward			
					if(pos.getX()==0 || pos.getX()==maze3d.getX_size()-1) continue;
					if(pos.getZ()==0 || pos.getZ()==maze3d.getZ_size()-1) continue;

					if(pos.getY()-2>=0&&((maze3d.getPositionValue(pos.getX(),pos.getY()-2,pos.getZ())==1)))
					{
						maze3d.setPostionValue(pos.getX(),pos.getY()-2,pos.getZ(),0);
						maze3d.setPostionValue(pos.getX(),pos.getY()-1,pos.getZ(),0);
						c.add(new Position(pos.getX(),pos.getY()-2,pos.getZ()));
						check++;
						breakLoop=true;
					}

					break;

				case 2:	//left		
					if(pos.getX()==0 || pos.getX()==maze3d.getX_size()-1) continue;
					if(pos.getZ()-2==0) continue;

					if(pos.getZ()-2>=0&&((maze3d.getPositionValue(pos.getX(),pos.getY(),pos.getZ()-2)==1)))
					{
						maze3d.setPostionValue(pos.getX(),pos.getY(),pos.getZ()-2,0);
						maze3d.setPostionValue(pos.getX(),pos.getY(),pos.getZ()-1,0);
						c.add(new Position(pos.getX(),pos.getY(),pos.getZ()-2));
						check++;
						breakLoop=true;
					}
					break;

				case 3: //down
					if(pos.getX()==maze3d.getX_size()-1||pos.getX()-2==0) continue;
					if(pos.getZ()==0 || pos.getZ()==maze3d.getZ_size()-1) continue;

					if(pos.getX()-2>=0&&((maze3d.getPositionValue(pos.getX()-2,pos.getY(),pos.getZ())==1)))
					{
						maze3d.setPostionValue(pos.getX()-1,pos.getY(),pos.getZ(),0);
						maze3d.setPostionValue(pos.getX()-2,pos.getY(),pos.getZ(),0);
						c.add(new Position(pos.getX()-2,pos.getY(),pos.getZ()));
						check++;
						breakLoop=true;
					}
					break;

				case 4: //forward		
					if(pos.getX()==0 || pos.getX()==-1) continue;
					if(pos.getZ()==0 || pos.getZ()==maze3d.getZ_size()-1) continue;

					if(pos.getY()+2<=maze3d.getY_size()&&((maze3d.getPositionValue(pos.getX(),pos.getY()+2,pos.getZ())==1))){
						maze3d.setPostionValue(pos.getX(),pos.getY()+2,pos.getZ(),0);
						maze3d.setPostionValue(pos.getX(),pos.getY()+1,pos.getZ(),0);
						c.add(new Position(pos.getX(),pos.getY()+2,pos.getZ()));
						check++;
						breakLoop=true;
					}
					break;

				case 5: //right
					if(pos.getX()==maze3d.getX_size()-1) continue;
					if(pos.getX()==0 || pos.getX()==maze3d.getX_size())continue;
					if(pos.getZ()+2==maze3d.getZ_size()-1) continue;

					if(pos.getZ()+2<=maze3d.getZ_size()&&((maze3d.getPositionValue(pos.getX(),pos.getY(),pos.getZ()+2)==1)))
					{
						maze3d.setPostionValue(pos.getX(),pos.getY(),pos.getZ()+1,0);
						maze3d.setPostionValue(pos.getX(),pos.getY(),pos.getZ()+2,0);
						c.add(new Position(pos.getX(),pos.getY(),pos.getZ()+2));
						check++;
						breakLoop=true;
					}


					break;

				case 6:
					if(pos.getX()==maze3d.getX_size()-1) continue;
					if(pos.getZ()==0||pos.getZ()==maze3d.getZ_size()-1) continue;

					Position tempPos=new Position(pos);
					tempPos.setX(pos.getX()+2);
					if((pos.getX()+2==maze3d.getX_size()-1)&&!(tempPos.equals(goal))) continue;

					if(pos.getX()+2<=maze3d.getX_size()&&((maze3d.getPositionValue(pos.getX()+2,pos.getY(),pos.getZ())==1))){
						maze3d.setPostionValue(pos.getX()+1,pos.getY(),pos.getZ(),0);
						maze3d.setPostionValue(pos.getX()+2,pos.getY(),pos.getZ(),0);
						c.add(new Position(pos.getX()+2,pos.getY(),pos.getZ()));
						check++;
						breakLoop=true;
					}
					break;
				}
			}

			if(check==0)
				c.remove(c.indexOf(pos));

		}while(!c.isEmpty());

		return maze3d;
	}



	//random a numbers from 1-7 range and put it into array, for random direction
	public Integer[] RandomDirections()
	{
		ArrayList<Integer> randoms = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++)
			randoms.add(i + 1);
		Collections.shuffle(randoms);

		return randoms.toArray(new Integer[6]);
	}

}
