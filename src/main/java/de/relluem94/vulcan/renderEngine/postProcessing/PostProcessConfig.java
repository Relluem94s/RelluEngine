package de.relluem94.vulcan.renderEngine.postProcessing;

public class PostProcessConfig {

private static int[] pospro = new int[PostProcessing.length];
	
	public static void init(){
		pospro[0] = 0;
		pospro[1] = 0;
		pospro[2] = 0;
		pospro[3] = 0;
		pospro[4] = 0;
		pospro[5] = 0;
	}
	
	private static void PostProcessing_custom(int array, int id){
		pospro[array] = id;
	}
	
	
	private static int id_0 = 0;
	private static int id_1 = 0;
	private static int id_2 = 0;
	private static int id_3 = 0;
	private static int id_4 = 0;
	private static int id_5 = 0;
	
	private static Integer count(int i){
		if(i > PostProcessing.ids){
			i = -1;
		}
		i++;
		return i;
		
	}
	
	public static void nextPostProcess(int id){
		switch(id){ 
        case 0: 
        	id_0 = count(id_0);
        	PostProcessing_custom(0,id_0);
            break; 
        case 1: 
        	id_1 = count(id_1);
        	PostProcessing_custom(1,id_1);
            break; 
        case 2: 
        	id_2 = count(id_2);
        	PostProcessing_custom(2,id_2);
            break; 
        case 3: 
        	id_3 = count(id_3);
        	PostProcessing_custom(3,id_3);
            break; 
        case 4: 
        	id_4 = count(id_4);
        	PostProcessing_custom(4,id_4);
            break; 
        case 5: 
        	id_5 = count(id_5);
        	PostProcessing_custom(5,id_5);
            break; 
        default: 
        	id_0 = count(id_0);
        	PostProcessing_custom(0,id_0);
        } 
		PostProcessing.update(pospro);
	}
	
	public static void setPostProcess(int id, int postid){
		if(id > PostProcessing.length && postid > PostProcessing.ids){
			PostProcessing_custom(id,postid);
			PostProcessing.update(pospro);
		}
	}
	
}
