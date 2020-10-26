package com.widget.miaotu.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;
import com.widget.miaotu.http.bean.ProvinceBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddresSelectUtils {


    public static void selectPICKview(ViewGroup viewGroup,Context mContext, SelectAddressCallBack selectAddressCallBack) {
        //  解析json填充集合
        //  省份
        ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
        //  城市
        ArrayList<List<String>> cityList = new ArrayList<>();

        ArrayList<List<List<String>>> districtList = new ArrayList<>();


        //  城市
        ArrayList<String> cities;

        ArrayList<List<String>> districts;

        //  区/县
        ArrayList<String> district;

        String province_data_json = FileUtil.getJson(mContext, "province_data.json");
        //  解析json数据
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(province_data_json);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        OptionsPickerView pickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String city1 = provinceBeanList.get(options1).getPickerViewText();

                if ("北京市".equals(city1) || "上海市".equals(city1) || "天津市".equals(city1) || "重庆市".equals(city1) || "澳门".equals(city1) || "香港".equals(city1)
                        || "台湾省".equals(city1)) {

                    if (selectAddressCallBack != null) {
                        selectAddressCallBack.selectAddressBack("",provinceBeanList.get(options1).getPickerViewText(),provinceBeanList.get(options1).getPickerViewText());
                    }
                } else {
                    if (selectAddressCallBack!=null){
                        selectAddressCallBack.selectAddressBack(provinceBeanList.get(options1).getPickerViewText(),cityList.get(options1).get(options2)
                                ,provinceBeanList.get(options1).getPickerViewText()+cityList.get(options1).get(options2));
                    }
                }

            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        })
                .isDialog(false)//是否设置为对话框模式
                .setDividerType(WheelView.DividerType.WRAP)
                .setCancelColor(Color.parseColor("#999999"))
                .setSubmitColor(Color.parseColor("#007AFF"))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)//设置滚轮文字大小
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)// default is true
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDecorView(viewGroup)
                .build();

        pickerView.setPicker(provinceBeanList, cityList, null);//添加数据源
        pickerView.show();
    }


    public interface SelectAddressCallBack {
        void selectAddressBack(String province, String city, String address);
    }

}
