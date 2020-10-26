package com.widget.miaotu.common.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import com.widget.miaotu.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Create By Anthony on 2016/1/15
 * Class Note:文件工具类
 * 包含内容：
 * 1 读取raw文件、file文件，drawable文件，asset文件，比如本地的json数据，本地文本等；
 * 如：String result =FileUtil.getString(context,"raw://first.json")
 * 2 读取本地的property文件，并转化为hashMap类型的数据（simpleProperty2HashMap）；
 * 3 将raw文件拷贝到指定目录（copyRawFile）；
 * 4 基本文件读写操作（readFile，writeFile）；
 * 5 从文件的完整路径名（路径+文件名）中提取 路径（extractFilePath）；
 * 6 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名)
 * 如：d:\path\file.ext --> file.ext（extractFileName）
 * 7 检查指定文件的路径是否存在（pathExists）
 * 8 检查制定文件是否存在（fileExists）
 * 9 创建目录（makeDir）
 * 10 移除字符串中的BOM前缀（removeBomHeaderIfExists）
 */
public class FileUtil {
    public static final String ASSETS_PREFIX = "file://android_assets/";
    public static final String ASSETS_PREFIX2 = "file://android_asset/";
    public static final String ASSETS_PREFIX3 = "assets://";
    public static final String ASSETS_PREFIX4 = "asset://";
    public static final String RAW_PREFIX = "file://android_raw/";
    public static final String RAW_PREFIX2 = "raw://";
    public static final String FILE_PREFIX = "file://";
    public static final String DRAWABLE_PREFIX = "drawable://";

    /**
     * 质量压缩方法
     *
     * @param
     * @return
     */
    public static Bitmap compressImage(String filePath, int size) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = size;
        Bitmap bitmap= BitmapFactory.decodeFile(filePath, opts);
        return compressImage(bitmap);
    }
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 512) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

        /**
         * 获取本地视频缩略图
         */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    public static String bitmap2File(Bitmap bitmap, String filePath) {

        File f = new File(filePath);
        if  (f.exists()) f.delete();
        FileOutputStream fOut = null;
        try  {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            fOut.flush();

            fOut.close();

        } catch (IOException e) {
            return  null;

        }
        return  f.getAbsolutePath();

    }

    public static String getJson(Context context, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }
//   public static InputStream getStream(Context context, String url) throws IOException {
//        String lowerUrl = url.toLowerCase();
//        InputStream is;
//        if (lowerUrl.startsWith(ASSETS_PREFIX)) {
//            String assetPath = url.substring(ASSETS_PREFIX.length());
//            is = getAssetsStream(context, assetPath);
//        } else if (lowerUrl.startsWith(ASSETS_PREFIX2)) {
//            String assetPath = url.substring(ASSETS_PREFIX2.length());
//            is = getAssetsStream(context, assetPath);
//        } else if (lowerUrl.startsWith(ASSETS_PREFIX3)) {
//            String assetPath = url.substring(ASSETS_PREFIX3.length());
//            is = getAssetsStream(context, assetPath);
//        } else if (lowerUrl.startsWith(ASSETS_PREFIX4)) {
//            String assetPath = url.substring(ASSETS_PREFIX4.length());
//            is = getAssetsStream(context, assetPath);
//        } else if (lowerUrl.startsWith(RAW_PREFIX)) {
//            String rawName = url.substring(RAW_PREFIX.length());
//            is = getRawStream(context, rawName);
//        } else if (lowerUrl.startsWith(RAW_PREFIX2)) {
//            String rawName = url.substring(RAW_PREFIX2.length());
//            is = getRawStream(context, rawName);
//        } else if (lowerUrl.startsWith(FILE_PREFIX)) {
//            String filePath = url.substring(FILE_PREFIX.length());
//            is = getFileStream(filePath);
//        } else if (lowerUrl.startsWith(DRAWABLE_PREFIX)) {
//            String drawableName = url.substring(DRAWABLE_PREFIX.length());
//            is = getDrawableStream(context, drawableName);
//        } else {
//            throw new IllegalArgumentException(String.format("Unsupported url: %s \n" +
//                    "Supported: \n%sxxx\n%sxxx\n%sxxx", url, ASSETS_PREFIX, RAW_PREFIX, FILE_PREFIX));
//        }
//        return is;
//    }
//
//    private static InputStream getAssetsStream(Context context, String path) throws IOException {
//        return context.getAssets().open(path);
//    }
//
//    private static InputStream getFileStream(String path) throws IOException {
//        return new FileInputStream(path);
//    }
//
//    private static InputStream getRawStream(Context context, String rawName) throws IOException {
//        int id = context.getResources().getIdentifier(rawName, "raw", context.getPackageName());
//        if (id != 0) {
//            try {
//                return context.getResources().openRawResource(id);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        throw new IOException(String.format("raw of id: %s from %s not found", id, rawName));
//    }
//
//    private static InputStream getDrawableStream(Context context, String rawName) throws IOException {
//        int id = context.getResources().getIdentifier(rawName, "drawable", context.getPackageName());
//        if (id != 0) {
//            BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(id);
//            Bitmap bitmap = drawable.getBitmap();
//
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);
//            return new ByteArrayInputStream(os.toByteArray());
//        }
//
//        throw new IOException(String.format("bitmap of id: %s from %s not found", id, rawName));
//    }
//
//    public static String getString(Context context, String url) throws IOException {
//        return getString(context, url, "UTF-8");
//    }
//
//    public static String getString(Context context, String url, String encoding) throws IOException {
//        String result = readStreamString(getStream(context, url), encoding);
//        if (result.startsWith("\ufeff")) {
//            result = result.substring(1);
//        }
//
//        return result;
//    }
//
//    public static String readStreamString(InputStream is, String encoding) throws IOException {
//        return new String(readStream(is), encoding);
//    }
//
//    public static byte[] readStream(InputStream is) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024 * 10];
//        int readlen;
//        while ((readlen = is.read(buf)) >= 0) {
//            baos.write(buf, 0, readlen);
//        }
//        baos.close();
//
//        return baos.toByteArray();
//    }
//
//    public static Bitmap getDrawableBitmap(Context context, String rawName) {
//        int id = context.getResources().getIdentifier(rawName, "drawable", context.getPackageName());
//        if (id != 0) {
//            BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(id);
//            if (drawable != null) {
//                return drawable.getBitmap();
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 读取Property文件
//     */
//    public static HashMap<String, String> simpleProperty2HashMap(Context context, String path) {
//        try {
//            InputStream is = getStream(context, path);
//            return simpleProperty2HashMap(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new HashMap<String, String>();
//    }
//
//    private static HashMap<String, String> simpleProperty2HashMap(InputStream in) throws IOException {
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//        Properties properties = new Properties();
//        properties.load(in);
//        in.close();
//        Set keyValue = properties.keySet();
//        for (Iterator it = keyValue.iterator(); it.hasNext(); ) {
//            String key = (String) it.next();
//            hashMap.put(key, (String) properties.get(key));
//        }
//
//        return hashMap;
//    }
//
//    /**
//     * 将raw文件拷贝到指定目录
//     */
//    public static void copyRawFile(Context ctx, String rawFileName, String to) {
//        String[] names = rawFileName.split("\\.");
//        String toFile = to + "/" + names[0] + "." + names[1];
//        File file = new File(toFile);
//        if (file.exists()) {
//            return;
//        }
//        try {
//            InputStream is = getStream(ctx, "raw://" + names[0]);
//            OutputStream os = new FileOutputStream(toFile);
//            int byteCount = 0;
//            byte[] bytes = new byte[1024];
//
//            while ((byteCount = is.read(bytes)) != -1) {
//                os.write(bytes, 0, byteCount);
//            }
//            os.close();
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 基本文件操作
//     */
//    public static String FILE_READING_ENCODING = "UTF-8";
//    public static String FILE_WRITING_ENCODING = "UTF-8";
//
//    public static String readFile(String _sFileName, String _sEncoding) throws Exception {
//        StringBuffer buffContent = null;
//        String sLine;
//
//        FileInputStream fis = null;
//        BufferedReader buffReader = null;
//        if (_sEncoding == null || "".equals(_sEncoding)) {
//            _sEncoding = FILE_READING_ENCODING;
//        }
//
//        try {
//            fis = new FileInputStream(_sFileName);
//            buffReader = new BufferedReader(new InputStreamReader(fis,
//                    _sEncoding));
//            boolean zFirstLine = "UTF-8".equalsIgnoreCase(_sEncoding);
//            while ((sLine = buffReader.readLine()) != null) {
//                if (buffContent == null) {
//                    buffContent = new StringBuffer();
//                } else {
//                    buffContent.append("\n");
//                }
//                if (zFirstLine) {
//                    sLine = removeBomHeaderIfExists(sLine);
//                    zFirstLine = false;
//                }
//                buffContent.append(sLine);
//            }// end while
//            return (buffContent == null ? "" : buffContent.toString());
//        } catch (FileNotFoundException ex) {
//            throw new Exception("要读取的文件没有找到!", ex);
//        } catch (IOException ex) {
//            throw new Exception("读取文件时错误!", ex);
//        } finally {
//            // 增加异常时资源的释放
//            try {
//                if (buffReader != null)
//                    buffReader.close();
//                if (fis != null)
//                    fis.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    public static File writeFile(InputStream is, String path, boolean isOverride) throws Exception {
//        String sPath = extractFilePath(path);
//        if (!pathExists(sPath)) {
//            makeDir(sPath, true);
//        }
//
//        if (!isOverride && fileExists(path)) {
//            if (path.contains(".")) {
//                String suffix = path.substring(path.lastIndexOf("."));
//                String pre = path.substring(0, path.lastIndexOf("."));
//                path = pre + "_" + TimeUtils.getNowTime() + suffix;
//            } else {
//                path = path + "_" + TimeUtils.getNowTime();
//            }
//        }
//
//        FileOutputStream os = null;
//        File file = null;
//
//        try {
//            file = new File(path);
//            os = new FileOutputStream(file);
//            int byteCount = 0;
//            byte[] bytes = new byte[1024];
//
//            while ((byteCount = is.read(bytes)) != -1) {
//                os.write(bytes, 0, byteCount);
//            }
//            os.flush();
//
//            return file;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("写文件错误", e);
//        } finally {
//            try {
//                if (os != null)
//                    os.close();
//                if (is != null)
//                    is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static File writeFile(String path, String content, String encoding, boolean isOverride) throws Exception {
//        if (TextUtils.isEmpty(encoding)) {
//            encoding = FILE_WRITING_ENCODING;
//        }
//        InputStream is = new ByteArrayInputStream(content.getBytes(encoding));
//        return writeFile(is, path, isOverride);
//    }


    /**
     * 根据URI转文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }



    /**
     * 启动裁剪
     *
     * @param activity       上下文
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @param requestCode    比如：UCrop.REQUEST_CROP
     * @param aspectRatioX   裁剪图片宽高比
     * @param aspectRatioY   裁剪图片宽高比
     * @return
     */
    public static String startUCrop(Activity activity, String sourceFilePath,
                                    int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
        //裁剪后图片的绝对路径
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start(activity, requestCode);
        return cameraScalePath;
    }

//
//    /**
//     * 从文件的完整路径名（路径+文件名）中提取 路径（包括：Drive+Directroy )
//     *
//     * @param _sFilePathName
//     * @return
//     */
//    public static String extractFilePath(String _sFilePathName) {
//        int nPos = _sFilePathName.lastIndexOf('/');
//        if (nPos < 0) {
//            nPos = _sFilePathName.lastIndexOf('\\');
//        }
//
//        return (nPos >= 0 ? _sFilePathName.substring(0, nPos + 1) : "");
//    }
//
//    /**
//     * 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名) <br>
//     * 如：d:\path\file.ext --> file.ext
//     *
//     * @param _sFilePathName
//     * @return
//     */
//    public static String extractFileName(String _sFilePathName) {
//        return extractFileName(_sFilePathName, File.separator);
//    }
//
//    /**
//     * 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名) <br>
//     * 如：d:\path\file.ext --> file.ext
//     *
//     * @param _sFilePathName  全文件路径名
//     * @param _sFileSeparator 文件分隔符
//     * @return
//     */
//    public static String extractFileName(String _sFilePathName,
//                                         String _sFileSeparator) {
//        int nPos = -1;
//        if (_sFileSeparator == null) {
//            nPos = _sFilePathName.lastIndexOf(File.separatorChar);
//            if (nPos < 0) {
//                nPos = _sFilePathName
//                        .lastIndexOf(File.separatorChar == '/' ? '\\' : '/');
//            }
//        } else {
//            nPos = _sFilePathName.lastIndexOf(_sFileSeparator);
//        }
//
//        if (nPos < 0) {
//            return _sFilePathName;
//        }
//
//        return _sFilePathName.substring(nPos + 1);
//    }
//
//    /**
//     * 检查指定文件的路径是否存在
//     *
//     * @param _sPathFileName 文件名称(含路径）
//     * @return 若存在，则返回true；否则，返回false
//     */
//    public static boolean pathExists(String _sPathFileName) {
//        String sPath = extractFilePath(_sPathFileName);
//        return fileExists(sPath);
//    }
//
//    public static boolean fileExists(String _sPathFileName) {
//        File file = new File(_sPathFileName);
//        return file.exists();
//    }
//
//    /**
//     * 创建目录
//     *
//     * @param _sDir             目录名称
//     * @param _bCreateParentDir 如果父目录不存在，是否创建父目录
//     * @return
//     */
//    public static boolean makeDir(String _sDir, boolean _bCreateParentDir) {
//        boolean zResult = false;
//        File file = new File(_sDir);
//        if (_bCreateParentDir)
//            zResult = file.mkdirs(); // 如果父目录不存在，则创建所有必需的父目录
//        else
//            zResult = file.mkdir(); // 如果父目录不存在，不做处理
//        if (!zResult)
//            zResult = file.exists();
//        return zResult;
//    }
//
//    /**
//     * 移除字符串中的BOM前缀
//     *
//     * @param _sLine 需要处理的字符串
//     * @return 移除BOM后的字符串.
//     */
//    private static String removeBomHeaderIfExists(String _sLine) {
//        if (_sLine == null) {
//            return null;
//        }
//        String line = _sLine;
//        if (line.length() > 0) {
//            char ch = line.charAt(0);
//            // 使用while是因为用一些工具看到过某些文件前几个字节都是0xfffe.
//            // 0xfeff,0xfffe是字节序的不同处理.JVM中,一般是0xfeff
//            while ((ch == 0xfeff || ch == 0xfffe)) {
//                line = line.substring(1);
//                if (line.length() == 0) {
//                    break;
//                }
//                ch = line.charAt(0);
//            }
//        }
//        return line;
//    }
//
//    /**
//     * get file type
//     */
//    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
//
//    static {
//        //images
//        mFileTypes.put("FFD8FF", "jpg");
//        mFileTypes.put("89504E47", "png");
//        mFileTypes.put("89504E", "png");
//        mFileTypes.put("47494638", "gif");
////        mFileTypes.put("49492A00", "tif");
////        mFileTypes.put("424D", "bmp");
////        mFileTypes.put("41433130", "dwg"); //CAD
////        mFileTypes.put("38425053", "psd");
////        mFileTypes.put("7B5C727466", "rtf"); //日记本
////        mFileTypes.put("3C3F786D6C", "xml");
////        mFileTypes.put("68746D6C3E", "html");
////        mFileTypes.put("44656C69766572792D646174653A", "eml"); //邮件
////        mFileTypes.put("D0CF11E0", "doc");
////        mFileTypes.put("5374616E64617264204A", "mdb");
////        mFileTypes.put("252150532D41646F6265", "ps");
////        mFileTypes.put("255044462D312E", "pdf");
////        mFileTypes.put("504B0304", "zip");
////        mFileTypes.put("52617221", "rar");
////        mFileTypes.put("57415645", "wav");
////        mFileTypes.put("41564920", "avi");
////        mFileTypes.put("2E524D46", "rm");
////        mFileTypes.put("000001BA", "mpg");
////        mFileTypes.put("000001B3", "mpg");
////        mFileTypes.put("6D6F6F76", "mov");
////        mFileTypes.put("3026B2758E66CF11", "asf");
////        mFileTypes.put("4D546864", "mid");
////        mFileTypes.put("1F8B08", "gz");
////        mFileTypes.put("", "");
////        mFileTypes.put("", "");
//    }
//
//    public static String getFileType(String filePath) {
//        return mFileTypes.get(getFileHeader(filePath));
//    }
//
//    //获取文件头信息
//    public static String getFileHeader(String filePath) {
//        FileInputStream is = null;
//        String value = null;
//        try {
//            is = new FileInputStream(filePath);
//            byte[] b = new byte[3];
//            is.read(b, 0, b.length);
//            value = bytesToHexString(b);
//        } catch (Exception e) {
//        } finally {
//            if (null != is) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        return value;
//    }
//
//    private static String bytesToHexString(byte[] src) {
//        StringBuilder builder = new StringBuilder();
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        String hv;
//        for (int i = 0; i < src.length; i++) {
//            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
//            if (hv.length() < 2) {
//                builder.append(0);
//            }
//            builder.append(hv);
//        }
//        return builder.toString();
//    }
//
//    public static String getUrlFileName(String url) {
//        return url.substring(url.lastIndexOf("/") + 1);
//    }
//
//    public static String getUrlPath(String url) {
//        return url.substring(0, url.lastIndexOf("/") + 1);
//    }
//
//    private static String getSuffix(File file) {
//        if (file == null || !file.exists() || file.isDirectory()) {
//            return null;
//        }
//        String fileName = file.getName();
//        if (fileName.equals("") || fileName.endsWith(".")) {
//            return null;
//        }
//        int index = fileName.lastIndexOf(".");
//        if (index != -1) {
//            return fileName.substring(index + 1).toLowerCase(Locale.US);
//        } else {
//            return null;
//        }
//    }
//
//    public static String getMimeType(File file) {
//        String suffix = getSuffix(file);
//        if (suffix == null) {
//            return "file/*";
//        }
//        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
//        if (type != null || !type.isEmpty()) {
//            return type;
//        }
//        return "file/*";
//    }

}
