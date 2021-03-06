# ImageSelector
## ImageSelector是一个图片选择框架，轻松实现图片选择功能。
###效果图
 ![github](https://github.com/AndroidKun/ImageSelector/blob/master/images/Screenshot_2016-09-13-16-11-21.png)
  ![github](https://github.com/AndroidKun/ImageSelector/blob/master/images/Screenshot_2016-09-13-16-12-21.png)
### Gradle

    compile 'com.androidkun:imageselector:1.0.1'
### 使用方法
#### 1.处理按钮点击事件

    /**
     * PopupWindow
     */
    private SelectMothedPopupWindow selectMothedPopupWindow;
    /**
     * 可选择图片的最大数量
     */
    private int selectNum = 3;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage(){
        if(selectMothedPopupWindow == null){
            selectMothedPopupWindow = new SelectMothedPopupWindow(this);
        }
        selectMothedPopupWindow.show(this,findViewById(R.id.main),this);
    }


#### 2.监听PopupWindow点击事件

    @Override
    public void mothedSelected(int mothed) {
        if (mothed == 1) {//相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1002);
        } else if (mothed == 2) {//相册
            startActivityForResult(new Intent(this, SelectImageActivity.class).putExtra("SELECT_NUM", selectNum), 1001);
        }
    }
 
#### 3.接收返回结果

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {//相册回结果
            if (resultCode == 1001) {
                List<String> images = (List<String>) data.getSerializableExtra("SELECTED_IMAGE");
                String image = "";
                for (String path : images) {
                    image += path + "\n";
                }
                Log.w("TAG",image);
            }
        }else if(requestCode == 1002) {//相机返回结果
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                String path = saveBitmapToLoacte(bitmap);
                Log.w("TAG",path);
            }
        }
     }
 

 
