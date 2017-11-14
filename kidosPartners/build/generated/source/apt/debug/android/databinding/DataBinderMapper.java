
package android.databinding;
import com.example.kidospartners.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 23;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.example.kidospartners.R.layout.activity_kidos_partners_class_details:
                    return com.example.kidospartners.databinding.ActivityKidosPartnersClassDetailsBinding.bind(view, bindingComponent);
                case com.example.kidospartners.R.layout.activity_kidos_partners_activity_details:
                    return com.example.kidospartners.databinding.ActivityKidosPartnersActivityDetailsBinding.bind(view, bindingComponent);
                case com.example.kidospartners.R.layout.activity_kidos_partners_contact_details:
                    return com.example.kidospartners.databinding.ActivityKidosPartnersContactDetailsBinding.bind(view, bindingComponent);
                case com.example.kidospartners.R.layout.layout_kidos_partners_batcheslist_item:
                    return com.example.kidospartners.databinding.LayoutKidosPartnersBatcheslistItemBinding.bind(view, bindingComponent);
                case com.example.kidospartners.R.layout.activity_kidos_partners_registration:
                    return com.example.kidospartners.databinding.ActivityKidosPartnersRegistrationBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -1795129192: {
                if(tag.equals("layout/activity_kidos_partners_class_details_0")) {
                    return com.example.kidospartners.R.layout.activity_kidos_partners_class_details;
                }
                break;
            }
            case 296755095: {
                if(tag.equals("layout/activity_kidos_partners_activity_details_0")) {
                    return com.example.kidospartners.R.layout.activity_kidos_partners_activity_details;
                }
                break;
            }
            case -376164800: {
                if(tag.equals("layout/activity_kidos_partners_contact_details_0")) {
                    return com.example.kidospartners.R.layout.activity_kidos_partners_contact_details;
                }
                break;
            }
            case -102719412: {
                if(tag.equals("layout/layout_kidos_partners_batcheslist_item_0")) {
                    return com.example.kidospartners.R.layout.layout_kidos_partners_batcheslist_item;
                }
                break;
            }
            case -895060098: {
                if(tag.equals("layout/activity_kidos_partners_registration_0")) {
                    return com.example.kidospartners.R.layout.activity_kidos_partners_registration;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"
            ,"activitydetails"
            ,"batchdetails"
            ,"classdetails"
            ,"contactdetails"
            ,"registrationdetails"};
    }
}