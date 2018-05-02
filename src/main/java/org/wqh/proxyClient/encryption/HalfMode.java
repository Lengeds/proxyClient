package org.wqh.proxyClient.encryption;
/*
 * 使用二分法置换进行加密和解密,解密和加密都是三次置换，操作对象是一个字节
 */
public class HalfMode extends Mode<Integer>{
	//置换次数
	public static int exNum=3;
	//每次置换的位数
	public static int ex[]=new int[]{4,2,1};
    public  HalfMode() {
    	dataBits = Byte.SIZE;
	}
    @Override
    public Integer encrypt(Integer d) {
    	
        return exchange(d);
    }
   
    @Override
    public Integer decrypt(Integer d) {
        return exchange(d);
    }
    
    private Integer exchange(Integer b){
    	Integer result;
    	int temp[]=new int[14];
		//第一次交换，以整个字节为操作对象，高四位和低四位交换
		temp[0]=(b<<ex[0])&(0xf0);
    	temp[1]=(b>>ex[0])&(0x0f);
    	//第二次交换，，以第一操作得到的两个结果为操作对象，高二位和低二位交换
    	temp[2] = (temp[0]<<ex[1])&(0xc0);
    	temp[3] = (temp[0]>>ex[1])&(0x30);
    	temp[4] = (temp[1]<<ex[1])&(0x0c);
    	temp[5] = (temp[1]>>ex[1])&(0x03);
    	//第三次交换，同理以上
    	temp[6] = (temp[2]<<ex[2])&(0x80);
    	temp[7] = (temp[2]>>ex[2])&(0x40);
    	temp[8] = (temp[3]<<ex[2])&(0x20);
    	temp[9] = (temp[3]>>ex[2])&(0x10);
    	temp[10] = (temp[4]<<ex[2])&(0x08);
    	temp[11] = (temp[4]>>ex[2])&(0x04);
    	temp[12] = (temp[5]<<ex[2])&(0x02);
    	temp[13] = (temp[5]>>ex[2])&(0x01);
    	
    	 result= (temp[6]|temp[7]|temp[8]|temp[9]|temp[10]|temp[11]|temp[12]|temp[13]);
    	return result;
    }  
    
   
}
