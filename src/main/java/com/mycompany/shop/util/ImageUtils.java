package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

/**
 * Utility class for handling image operations in the shop application
 */
public class ImageUtils {
    
    // Thư mục lưu trữ ảnh sản phẩm
    private static final String IMAGE_DIRECTORY = "images/products/";
    private static final String[] SUPPORTED_FORMATS = {"jpg", "jpeg", "png", "gif", "bmp"};
    private static final int MAX_IMAGE_WIDTH = 300;
    private static final int MAX_IMAGE_HEIGHT = 300;
    
    static {
        // Tạo thư mục images nếu chưa tồn tại
        createImageDirectory();
    }
    
    /**
     * Tạo thư mục lưu trữ ảnh nếu chưa tồn tại
     */
    private static void createImageDirectory() {
        try {
            Path imagePath = Paths.get(IMAGE_DIRECTORY);
            if (!Files.exists(imagePath)) {
                Files.createDirectories(imagePath);
            }
        } catch (IOException e) {
            System.err.println("Không thể tạo thư mục ảnh: " + e.getMessage());
        }
    }
    
    /**
     * Mở dialog chọn file ảnh
     * @param parent Component cha
     * @return File được chọn hoặc null nếu hủy
     */
    public static File chooseImageFile(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
        
        // Chỉ cho phép chọn file ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", SUPPORTED_FORMATS);
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    
    /**
     * Lưu ảnh vào thư mục sản phẩm với tên file dựa trên mã sản phẩm
     * @param sourceFile File ảnh gốc
     * @param productCode Mã sản phẩm
     * @return Đường dẫn tương đối của ảnh đã lưu hoặc null nếu lỗi
     */
    public static String saveProductImage(File sourceFile, String productCode) {
        if (sourceFile == null || !sourceFile.exists()) {
            return null;
        }
        
        try {
            // Lấy extension của file gốc
            String fileName = sourceFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            
            // Tạo tên file mới dựa trên mã sản phẩm
            String newFileName = productCode + extension;
            Path targetPath = Paths.get(IMAGE_DIRECTORY + newFileName);
            
            // Copy file vào thư mục đích
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            // Trả về đường dẫn tương đối
            return IMAGE_DIRECTORY + newFileName;
            
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu ảnh: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Tạo ImageIcon từ đường dẫn ảnh với kích thước cố định
     * @param imagePath Đường dẫn ảnh
     * @param width Chiều rộng mong muốn
     * @param height Chiều cao mong muốn
     * @return ImageIcon hoặc null nếu lỗi
     */
    public static ImageIcon createImageIcon(String imagePath, int width, int height) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return createDefaultProductIcon(width, height);
        }
        
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                return createDefaultProductIcon(width, height);
            }
            
            BufferedImage originalImage = ImageIO.read(imageFile);
            if (originalImage == null) {
                return createDefaultProductIcon(width, height);
            }
            
            // Resize ảnh
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
            
        } catch (IOException e) {
            System.err.println("Lỗi khi tạo ImageIcon: " + e.getMessage());
            return createDefaultProductIcon(width, height);
        }
    }
    
    /**
     * Tạo icon mặc định cho sản phẩm khi không có ảnh
     * @param width Chiều rộng
     * @param height Chiều cao
     * @return ImageIcon mặc định
     */
    public static ImageIcon createDefaultProductIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // Vẽ background
        g2d.setColor(ModernTheme.BACKGROUND_LIGHT);
        g2d.fillRect(0, 0, width, height);
        
        // Vẽ border
        g2d.setColor(ModernTheme.BORDER_COLOR);
        g2d.drawRect(0, 0, width - 1, height - 1);
        
        // Vẽ text "No Image"
        g2d.setColor(ModernTheme.TEXT_SECONDARY);
        g2d.setFont(ModernTheme.FONT_BODY);
        FontMetrics fm = g2d.getFontMetrics();
        String text = "No Image";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Xóa ảnh sản phẩm
     * @param imagePath Đường dẫn ảnh cần xóa
     * @return true nếu xóa thành công
     */
    public static boolean deleteProductImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return true;
        }
        
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                return imageFile.delete();
            }
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa ảnh: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Kiểm tra xem file có phải là ảnh hợp lệ không
     * @param file File cần kiểm tra
     * @return true nếu là ảnh hợp lệ
     */
    public static boolean isValidImageFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        
        String fileName = file.getName().toLowerCase();
        for (String format : SUPPORTED_FORMATS) {
            if (fileName.endsWith("." + format)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Lấy đường dẫn thư mục ảnh
     * @return Đường dẫn thư mục ảnh
     */
    public static String getImageDirectory() {
        return IMAGE_DIRECTORY;
    }
}
