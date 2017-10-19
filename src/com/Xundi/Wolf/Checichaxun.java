package com.Xundi.Wolf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Checi.Checi;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttransportation.R;

public class Checichaxun extends Activity {

	private AutoCompleteTextView autoCompleteTextView;
	private TextView textView;
	private EditText editText;
	private ArrayAdapter adapter;
	//所有支持的城市列表
	String zhandian[] = { "六安", "高雄", "保山", "滨州", "滁州", "资阳", "昌吉", "淄博", "徐州",
			"伊宁", "株洲", "银川", "扬州", "鹤岗", "大连", "温州", "景德镇", "孝感", "佳木斯", "宿州",
			"寿光", "赤峰", "庆阳", "岳阳", "南京", "南通", "马鞍山", "双鸭山", "天津", "仪征", "绥化",
			"祁县", "庄河", "驻马店", "安宁", "邢台", "崇左", "伊犁", "启东", "思茅", "抚顺", "昭通",
			"宜昌", "丹东", "南安", "三门峡", "吉林", "靖江", "南宁", "九江", "梧州", "揭阳", "溧阳",
			"武威", "姜堰", "云浮", "日喀则", "涪陵", "池州", "巴彦淖尔", "文登", "亳州", "曲靖",
			"丽水", "河池", "鹤壁", "佛山", "普洱", "营口", "蚌埠", "平湖", "集宁", "六盘水", "雅安",
			"巩义", "长春", "随州", "威海", "伊春", "景洪", "仙桃", "大同", "盐城", "永康", "从化",
			"和田", "常熟", "临海", "包头", "丽江", "定西", "太原", "铜陵", "奎屯", "江都", "泸州",
			"上饶", "酒泉", "邵阳", "葫芦岛", "肇东", "通化", "枣庄", "无锡", "牙克石", "遂宁", "香港",
			"广州", "即墨", "赣州", "咸阳", "菏泽", "乐昌", "旅顺", "黑河", "兖州", "高州", "黄石",
			"晋城", "济南", "呼伦贝尔", "莱州", "永州", "鹰潭", "周口", "德州", "潜江", "安庆", "建德",
			"乌兰浩特", "兰州", "青岛", "鄂州", "兴义", "天长", "瓦房店", "张家口", "绍兴", "荆门",
			"阳泉", "开远", "桂林", "增城", "阳江", "宁德", "齐齐哈尔", "黄山", "都江堰", "平度",
			"江阴", "泉州", "琼海", "海门", "潞西", "张家港", "安康", "迁安", "天水", "朝阳", "乌海",
			"上虞", "灵宝", "东营", "济宁", "都匀", "自贡", "龙岩", "武汉", "泰兴", "宁海", "普兰店",
			"嘉峪关", "江门", "昆明", "余姚", "澳门", "惠州", "咸宁", "开平", "张掖", "句容", "吉安",
			"绵阳", "承德", "莆田", "福州", "宜兴", "蓬莱", "慈溪", "芜湖", "钦州", "朔州", "铜仁",
			"沧州", "醴陵", "莱西", "嘉善", "辽源", "四平", "商洛", "鞍山", "厦门", "广元", "天门",
			"白银", "黔南", "瑞安", "泰州", "太仓", "攀枝花", "镇江", "黄冈", "衡水", "建水", "忻州",
			"临汾", "石林", "临安", "临沂", "吴中", "宣城", "临沧", "宿豫", "拉萨", "贵港", "延安",
			"诸城", "中山", "上海", "昆山", "日照", "常州", "东莞", "聊城", "赤水", "延吉", "唐山",
			"七台河", "天柱", "梅河口", "张家界", "赣榆", "连云港", "南昌", "个旧", "乌鲁木齐", "遵义",
			"柳州", "喀什", "敦化", "宁波", "烟台", "凯里", "益阳", "济源", "桐乡", "焦作", "项城",
			"城固", "抚州", "湘乡", "阜新", "荣成", "台州", "汕头", "奉化", "南充", "锦州", "漯河",
			"象山", "西双版纳", "湖州", "肥城", "邯郸", "玉环", "河源", "龙海", "高邮", "神农架",
			"扬中", "商丘", "淮安", "大庆", "滕州", "石家庄", "白城", "石河子", "信阳", "清镇", "大理",
			"格尔木", "乐山", "淮南", "深圳", "安阳", "平凉", "桐庐", "嘉兴", "潮州", "宝鸡", "南阳",
			"新沂", "长沙", "金坛", "沈阳", "台北", "新乡", "湛江", "平顶山", "玉溪", "乳山", "义乌",
			"通州", "宿迁", "三亚", "合肥", "榆林", "宜宾", "北海", "襄樊", "东台", "海宁", "萍乡",
			"珠海", "苏州", "晋江", "金华", "吴江", "濮阳", "南平", "吐鲁番", "鄂尔多斯", "西安",
			"綦江", "荆州", "西宁", "舟山", "重庆", "永安", "腾冲", "辽阳", "郴州", "广安", "富阳",
			"汕尾", "呼和浩特", "胶州", "阜阳", "介休", "安顺", "德阳", "肇庆", "漳州", "阳春", "泗阳",
			"松原", "本溪", "茂名", "温岭", "贵阳", "铜川", "牡丹江", "达州", "白山", "海城", "清远",
			"台山", "楚雄", "赤壁", "固原", "宁乡", "秦皇岛", "武安", "巢湖", "克拉玛依", "淮北",
			"长治", "百色", "杭州", "湘西", "衡阳", "丹阳", "玉林", "章丘", "运城", "贺州", "梅州",
			"怀化", "娄底", "邳州", "临夏", "衢州", "沭阳", "北京", "顺德", "保定", "眉山", "三明",
			"东阳", "金昌", "长海", "青州", "哈密", "湘潭", "郑州", "泰安", "诸暨", "江津", "莱芜",
			"四会", "宜春", "十堰", "新余", "延边", "明光", "库尔勒", "勉县", "毕节", "西昌",
			"香格里拉", "汉中", "如皋", "普宁", "恩施", "防城港", "巴中", "连州", "哈尔滨", "廊坊",
			"成都", "盘锦", "塔城", "合川", "开封", "长乐", "石狮", "常德", "许昌", "渭南", "海口",
			"兴化", "吉首", "吕梁", "晋中", "来宾", "内江", "韶关", "潍坊", "花都", "洛阳", "通辽",
			"铁岭", "鸡西" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checichaxun);
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, zhandian);
		autoCompleteTextView.setAdapter(adapter);
		editText = (EditText) findViewById(R.id.editText1);
		textView = (TextView) findViewById(R.id.textView1);
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.checichaxun, menu);
//		return true;
//	}
	//使用线程接收返回信息
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			String jsondate=(String) msg.obj;
			StringBuffer buffer=new StringBuffer();
			try {
				JSONObject jsonObject=new JSONObject(jsondate);
				if (jsonObject.getString("reason").equals("success")) {
					Toast.makeText(Checichaxun.this, "查询成功", 0).show();
					JSONArray array=new JSONArray(jsonObject.getString("result"));
					for (int i = 0; i < array.length()-1; i++) {
						JSONObject jsonObject2=array.getJSONObject(i);
						 String station=jsonObject2.getString("name");
						 String starttime=jsonObject2.getString("start_time");
						 String endtime=jsonObject2.getString("end_time");
						 String time=starttime.substring(0, 2);
						 String time1=starttime.substring(2, 4);
						 String time2=endtime.substring(0, 2);
						 String time12=endtime.substring(2, 4);
						 buffer.append(station+"  "+time+":"+time1+"--"+time2+":"+time12+"\n\n");
						JSONArray array2=new JSONArray(jsonObject2.getString("stationdes"));
						for (int j = 0; j < array2.length()-1; j++) {
							JSONObject jsonObject3=array2.getJSONObject(j);
							int stationNum=jsonObject3.getInt("stationNum");
							String name=jsonObject3.getString("name");
							buffer.append(stationNum+":"+name+"\n");
							
							
						}
						
					}
					textView.setText(buffer);
					
					
				}else{
					Toast.makeText(Checichaxun.this, "查询失败", 0).show();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
	};
	//查询按钮监听事件
	public void huang(View v){
		textView.setText("");
		String city=autoCompleteTextView.getText().toString();
		String q=editText.getText().toString();
		Checi.getRequest1(handler, city, q);
	}

}
