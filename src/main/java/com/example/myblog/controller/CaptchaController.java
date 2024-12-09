package com.example.myblog.controller;

import com.example.myblog.entity.User;
import com.example.myblog.entity.po.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/***
 *@title CaptchaController
 *@CreateTime 2024/11/24 21:04
 *@description CaptchaController
 **/

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 生成验证码图片并存储到 Redis
     *
     * @param key 验证码的唯一标识
     * @return 验证码图片的字节数组
     * @throws Exception 如果生成图片时发生错误
     */
    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateCaptcha(@RequestParam String key) throws Exception {
        // 生成随机验证码
        String captchaText = generateRandomText();
        // 存储验证码到 Redis，设置过期时间为 60 秒
        redisTemplate.opsForValue().set(key, captchaText, 60, TimeUnit.SECONDS);

        int width = 150;
        int height = 50;

        // 创建验证码图片
        BufferedImage image = createCaptchaImage(captchaText, width, height);

        // 将图片转换为字节数组
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", pngOutputStream);

        return pngOutputStream.toByteArray();
    }

    /**
     * 验证用户输入的验证码是否正确
     *
     * @param captcha captcha 对象，包含验证码密钥和用户输入的验证码
     * @return 验证结果，true 表示验证成功，false 表示验证失败
     */
    @PostMapping("/verify")
    public boolean verify(@RequestBody Captcha captcha) {
        String key = captcha.getCaptchaKey();
        String userInput = captcha.getCaptcha();

        // 从 Redis 获取存储的验证码
        String storedCaptcha = redisTemplate.opsForValue().get(key);
        if (storedCaptcha == null) {
            return false; // 验证码不存在或已过期
        }

        // 验证输入的验证码是否匹配
        boolean isValid = storedCaptcha.equalsIgnoreCase(userInput);

        return isValid;
    }

    /**
     * 生成随机的4位数字验证码
     *
     * @return 随机生成的4位数字字符串
     */
    private String generateRandomText() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10)); // 生成一个随机数字
        }
        return sb.toString();
    }

    /**
     * 创建验证码图片
     *
     * @param captchaText 验证码文本
     * @param width       图片宽度
     * @param height      图片高度
     * @return 验证码图片
     */
    private BufferedImage createCaptchaImage(String captchaText, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        Random random = new Random();

        // 设置背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置文本颜色和字体
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));

        // 绘制验证码文本
        g2d.drawString(captchaText, 50, 35);

        // 添加一些噪声（可选）
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g2d.drawOval(x, y, 1, 1);
        }

        g2d.dispose();
        return image;
    }
}