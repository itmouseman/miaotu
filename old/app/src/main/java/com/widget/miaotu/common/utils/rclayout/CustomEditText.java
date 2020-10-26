package com.widget.miaotu.common.utils.rclayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.AndroidUtils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/10/21.
 *
 * @author tzy
 *         自定义组合的EditText控件
 */

public class CustomEditText extends RelativeLayout implements View.OnClickListener {
    /**
     * 清除
     */
    private static final int STYLE_CLEAN = 1;
    /**
     * 眼睛
     */
    private static final int STYLE_SEE = 2;
    /**
     * 验证码
     */
    private static final int STYLE_COUNTDOWN = 3;
    /**
     * 数字
     */
    private static final String INPUT_NUMBER = "number";
    /**
     * 密码
     */
    private static final String INPUT_PASSWORD = "password";

    private ImageView imageClean;
    private EditText editInput;
    private ImageView imageEyeSee;
    private ImageView imageEyeClose;
    private TextView textCount;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化布局
        LayoutInflater.from(context).inflate(R.layout.edittext_grouped_controls, this, true);

        editInput = (EditText) findViewById(R.id.edit_input);

        imageClean = (ImageView) findViewById(R.id.image_clean);
        imageEyeSee = (ImageView) findViewById(R.id.image_eye_see);
        imageEyeClose = (ImageView) findViewById(R.id.image_eye_close);
        textCount = (TextView) findViewById(R.id.text_count);
        imageClean.setOnClickListener(this);
        imageEyeClose.setOnClickListener(this);
        imageEyeSee.setOnClickListener(this);

        //获取属性
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomEditTextStyle);

        if (attributes != null) {
            //组合控件的样式
            int attributesInt = attributes.getInt(R.styleable.CustomEditTextStyle_edit_style, 0);

            if (attributesInt == STYLE_CLEAN) {
                imageClean.setVisibility(VISIBLE);
            } else if (attributesInt == STYLE_SEE) {
                imageEyeSee.setVisibility(VISIBLE);
            } else if (attributesInt == STYLE_COUNTDOWN) {
                textCount.setVisibility(VISIBLE);
            }
            //EditText显示的hint
            String hint = attributes.getString(R.styleable.CustomEditTextStyle_text_hint);

            if (!AndroidUtils.isNullOrEmpty(hint)) {
                editInput.setHint(hint);
            }

            //EditText输入的类型
            String inputType = attributes.getString(R.styleable.CustomEditTextStyle_edit_input_type);

            if (!AndroidUtils.isNullOrEmpty(inputType)) {
                if(INPUT_NUMBER.equals(inputType)){
                    editInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                }else if(INPUT_PASSWORD.equals(inputType)){
                    editInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);//英文
                    setEdNoChinaese(editInput);
                }
            }
            //EditText输入的长度
            int inputLength = attributes.getInt(R.styleable.CustomEditTextStyle_edit_input_length, 25);
            editInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputLength)});

        }
        attributes.recycle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_clean:
                editInput.setText("");
                break;
            case R.id.image_eye_see:
                editInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageEyeClose.setVisibility(VISIBLE);
                imageEyeSee.setVisibility(GONE);
                break;
            case R.id.image_eye_close:
                editInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageEyeClose.setVisibility(GONE);
                imageEyeSee.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 获取验证码按钮
     * @return
     */
    public TextView getTextCount() {
        return textCount;
    }

    /**
     * 获取输入框输入的内容
     * @return
     */
    public String getInputText() {
        return editInput.getText().toString().trim();
    }

    /**
     * 设置内容
     * @param inputText
     */
    public void setInputText(String inputText) {
        editInput.setText(inputText);
    }

    /**
     * 获取EditText
     */
    public EditText getEditText() {
        return editInput;
    }

    /**
     * 设置输入框输入长度限制  按钮可点和不可点击
     * @param startIndex 开始的长度
     * @param endIndex   结束的长的
     * @param button     按钮
     */
    public void setLimitButtonClick(final int startIndex, final int endIndex, final Button button){
        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= startIndex && s.length() <= endIndex) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 限制edittext 不能输入中文
     * @param editText
     */
    public static void setEdNoChinaese(final EditText editText){
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = s.toString();
                //注意返回值是char数组
                char[] stringArr = txt.toCharArray();
                for (int i = 0; i < stringArr.length; i++) {
                    //转化为string
                    String value = new String(String.valueOf(stringArr[i]));
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(value);
                    if (m.matches()) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }
}
