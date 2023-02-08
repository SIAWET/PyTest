package com.example.pytest.app.util.fileUtil;

import android.content.Context;


import com.example.pytest.app.entity.chatEntity.EmojiEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class EmotionFileUtil {

    //读取JSON文件
    public static String readAssetsFile(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int fileLength = is.available();
            byte[] buffer = new byte[fileLength];
            int readLength = is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件是否存在";
    }

    public static List<EmojiEntity> parseEmotionList(String json) {
        List<EmojiEntity> emojiEntityList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("emoji_list");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    if (jsonObject1 != null) {
                        EmojiEntity mEmojiEntity = new EmojiEntity();
                        mEmojiEntity.setName(jsonObject1.optString("shortname", ""));
//                        mEmojiEntity.setUnicode(jsonObject1.optString("unicode", ""));
                        emojiEntityList.add(mEmojiEntity);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return emojiEntityList;
    }


}
