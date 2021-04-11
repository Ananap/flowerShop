package by.panasenko.flowershop.util;

public final class PagePath {
    public static final String INDEX = "index";
    public static final String ACCOUNT = "myAccount";
    public static final String PROFILE = "myProfile";
    public static final String BASKET = "basket";
    public static final String CHECKOUT = "checkout";
    public static final String ITEM_PAGE = "itemPage";
    public static final String ORDER_SUBMIT = "orderSubmittedPage";
    public static final String DIRECTION = "direction";
    public static final String FLOWER_DETAIL = "flowerDetail";
    public static final String ORDER_INFO = "admin/orderInfo";
    public static final String ORDER_INFO_REDIRECT = "redirect:/orderInfo";
    public static final String ADD_ADMIN = "admin/addAdmin";
    public static final String FLOWER_LIST = "admin/flowerList";
    public static final String ITEM_INFO = "admin/itemInfo";
    public static final String UPDATE_ITEM = "admin/updateItem";
    public static final String ADD_ITEM = "admin/addItem";
    public static final String DETAIL_ORDER = "admin/detailOrder";
    public static final String BAD_REQUEST = "common/badRequest";
    public static final String CHECKOUT_REDIRECT_MISS_FIELD = "redirect:/checkout?missingRequiredField=true&id=";
    public static final String FLOWER_LIST_REDIRECT = "redirect:flowerList";
    public static final String FLOWER_DETAIL_REDIRECT = "redirect:/flowerDetail?stock=true&id=";
    public static final String FLOWER_DETAIL_BASKET = "redirect:/flowerDetail?basket=true&id=";
    public static final String BASKET_REDIRECT = "redirect:basket";
    public static final String BASKET_FORWARD = "forward:/basket";
    public static final String PROFILE_REDIRECT = "redirect:myProfile";
    public static final String CHECKOUT_REDIRECT = "redirect:/checkout?id=";

    private PagePath(){}
}
