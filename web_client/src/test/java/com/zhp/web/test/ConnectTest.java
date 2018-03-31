package com.zhp.web.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ConnectTest {
    public static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
    public static final String agentKey = "User-Agent";

    @Test
    @Ignore
    public void get_zhilian_login() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(false);
        String url = "http://hr.zhaopin.com/hrclub/index.html";
        String loginUrl = "https://passport.zhaopin.com/org/login";
        String captUrl = "https://passport.zhaopin.com/chk/getCap?t";
        HtmlPage page = webClient.getPage(buildRequest(url));
        List<NameValuePair> resHeader = getWebResponse(page);
        HtmlAnchor btn = page.getAnchorByHref(loginUrl);
        HtmlPage page2 = btn.click();

        List<NameValuePair> resHeader2 = getWebResponse(page2);
        Date data = new Date();
        WebRequest wq = buildRequest(captUrl + data.getTime());
        buildGetCaptRequest(wq, page2.getBaseURL().toString());
        UnexpectedPage img = webClient.getPage(wq);
        byte[] bytesImg = getPageToByte(img);
        writeBytesToFile(bytesImg);
        restoreZhiLianCaptcha();
    }

    private void buildGetCaptRequest(WebRequest wq, String referer) {
        wq.setAdditionalHeader("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        wq.setAdditionalHeader("Accept-Encoding", "gzip, deflate, br");
        wq.setAdditionalHeader("Accept-Language", "zh-CN,zh;q=0.9");
        wq.setAdditionalHeader("Connection", "keep-alive");
        wq.setAdditionalHeader("Host", "passport.zhaopin.com");
        wq.setAdditionalHeader("Referer", referer);
    }

    public static void writeBytesToFile(byte[] bytes) throws IOException {
        OutputStream out = new FileOutputStream(System.getProperty("java.io.tmpdir") + "\\capt.jpg");
        InputStream is = new ByteArrayInputStream(bytes);
        byte[] buff = new byte[1024];
        int len;
        while ((len = is.read(buff)) != -1) {
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }

    // CHECKSTYLE:OFF
    public static byte[] getPageToByte(Page page) throws IOException {
        byte[] responseContent;
        WebResponse webResponse = null;
        try {
            webResponse = page.getWebResponse();
            int status = webResponse.getStatusCode();
            if (status != 200) {
                return null;
            }
            if (page.isHtmlPage()) {
                responseContent = ((HtmlPage) page).asXml().getBytes();
            } else {
                InputStream bodyStream = webResponse.getContentAsStream();
                responseContent = ByteStreams.toByteArray(bodyStream);
                bodyStream.close();
            }
        } finally {
            if (webResponse != null) {
                webResponse.cleanUp();
            }
        }
        return responseContent;
    }

    public static void restoreZhiLianCaptcha() throws Exception {
        int[] sequence = {10, 17, 14, 8, 1, 9, 4, 2, 3, 12, 19, 15, 11, 13, 6, 0, 5, 7, 16, 18,
                35, 26, 37, 34, 22, 30, 29, 33, 23, 27, 24, 31, 39, 32, 38, 21, 20, 36, 28, 25};
        int col = 20;
        int num;
        int x = 14;
        int y = 85;
        for (int i = 0; i < col * 2; i++) {
            num = i >= col ? 1 : 0;
            cutImage(System.getProperty("java.io.tmpdir") + "\\capt.jpg"
                    , System.getProperty("java.io.tmpdir") + "\\capt"
                            + i + ".jpg", sequence[i] % col * x, num * y, 14, 85);
        }
        String[] firstCow = new String[col];
        for (int i = 0; i < col; i++) {
            firstCow[i] = String.format(System.getProperty("java.io.tmpdir") + "\\capt%d.jpg", i);
        }
        mergeImage(firstCow, 1, System.getProperty("java.io.tmpdir") + "\\result1.jpg");
        String[] secondCow = new String[col];
        for (int i = 0; i < col; i++) {
            secondCow[i] = String.format(System.getProperty("java.io.tmpdir") + "\\capt%d.jpg", i + col);
        }
        mergeImage(secondCow, 1, System.getProperty("java.io.tmpdir") + "\\result2.jpg");
        mergeImage(new String[]{System.getProperty("java.io.tmpdir") + "\\result1.jpg",
                        System.getProperty("java.io.tmpdir") + "\\result2.jpg"}, 2,
                System.getProperty("java.io.tmpdir") + "\\result.jpg");

        String[] headerCow = new String[7];
        for (int i = 0; i < 7; i++) {
            headerCow[i] = String.format(System.getProperty("java.io.tmpdir") + "\\capt%d.jpg", i + col);
        }
        mergeImage(headerCow, 1, System.getProperty("java.io.tmpdir") + "\\headertmp.jpg");
        cutImage(System.getProperty("java.io.tmpdir") + "\\headertmp.jpg",
                System.getProperty("java.io.tmpdir") + "\\header.jpg" , 0, 45, 100, 40);

        for (int i = 0; i < col * 2; i++) {
            new File(System.getProperty("java.io.tmpdir") + "\\capt" + i + ".jpg").deleteOnExit();
        }
        new File(System.getProperty("java.io.tmpdir") + "\\result1.jpg").deleteOnExit();
        new File(System.getProperty("java.io.tmpdir") + "\\result2.jpg").deleteOnExit();
        new File(System.getProperty("java.io.tmpdir") + "\\headertmp.jpg").deleteOnExit();
    }


    public static void cutImage(String srcFile, String outFile, int x, int y,
                                int width, int height) throws Exception {
        FileInputStream fileInputStream = null;
        ImageInputStream imageInputStream = null;
        try {
            if (!new File(srcFile).exists()) {
                throw new FileNotFoundException(srcFile);
            }
            fileInputStream = new FileInputStream(srcFile);
            String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
            ImageReader reader = it.next();
            imageInputStream = ImageIO.createImageInputStream(fileInputStream);
            reader.setInput(imageInputStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            File tempOutFile = new File(outFile);
            if (!tempOutFile.exists()) {
                tempOutFile.mkdirs();
            }
            ImageIO.write(bi, ext, new File(outFile));
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (imageInputStream != null) {
                imageInputStream.close();
            }
        }
    }

    private static void mergeImage(String[] files, int type, String targetFile) throws IOException {
        int length = files.length;
        File[] src = new File[length];
        BufferedImage[] images = new BufferedImage[length];
        int[][] ImageArrays = new int[length][];
        for (int i = 0; i < length; i++) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }
        BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        int height_i = 0;
        int width_i = 0;
        for (int i = 0; i < images.length; i++) {
            if (type == 1) {
                ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                        images[i].getWidth());
                width_i += images[i].getWidth();
            } else if (type == 2) {
                ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                height_i += images[i].getHeight();
            }
        }
        ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));
    }

    // CHECKSTYLE:ON

    private List<NameValuePair> getWebResponse(HtmlPage page) {
        WebResponse response = page.getWebResponse();
        return response.getResponseHeaders();
    }

    private WebRequest buildRequest(String url) throws MalformedURLException {
        URL link = new URL(url);
        WebRequest request = new WebRequest(link);
        request.setAdditionalHeader(agentKey, userAgent);
        return request;
    }

    private Map<String, String> getResponseCookies(WebClient wc) {
        Set<Cookie> cookies = wc.getCookieManager().getCookies();
        Map<String, String> resCookies = Maps.newHashMap();
        cookies.stream().forEach(item -> resCookies.put(item.getName(), item.getValue()));
        return resCookies;
    }
}
