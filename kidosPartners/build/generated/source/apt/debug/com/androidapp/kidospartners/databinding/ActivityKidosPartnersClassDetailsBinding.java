package com.androidapp.kidospartners.databinding;
import com.androidapp.kidospartners.R;
import com.androidapp.kidospartners.BR;
import android.view.View;
public class ActivityKidosPartnersClassDetailsBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.label_name, 7);
        sViewsWithIds.put(R.id.label_address, 8);
        sViewsWithIds.put(R.id.label_area, 9);
        sViewsWithIds.put(R.id.label_city, 10);
        sViewsWithIds.put(R.id.label_state, 11);
        sViewsWithIds.put(R.id.label_pincode, 12);
    }
    // views
    public final android.widget.TextView labelAddress;
    public final android.widget.TextView labelArea;
    public final android.widget.TextView labelCity;
    public final android.widget.TextView labelName;
    public final android.widget.TextView labelPincode;
    public final android.widget.TextView labelState;
    private final android.widget.LinearLayout mboundView0;
    public final android.widget.EditText txtAddress;
    public final android.widget.EditText txtArea;
    public final android.widget.EditText txtCity;
    public final android.widget.EditText txtName;
    public final android.widget.EditText txtPincode;
    public final android.widget.EditText txtState;
    // variables
    private com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean mClassdetails;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private android.databinding.InverseBindingListener txtAddressandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of classdetails.addressline1
            //         is classdetails.setAddressline1((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(txtAddress);
            // localize variables for thread safety
            // classdetails.addressline1
            java.lang.String classdetailsAddressline1 = null;
            // classdetails
            com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;
            // classdetails != null
            boolean classdetailsJavaLangObjectNull = false;



            classdetailsJavaLangObjectNull = (classdetails) != (null);
            if (classdetailsJavaLangObjectNull) {




                classdetails.setAddressline1(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener txtAreaandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of classdetails.area
            //         is classdetails.setArea((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(txtArea);
            // localize variables for thread safety
            // classdetails
            com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;
            // classdetails.area
            java.lang.String classdetailsArea = null;
            // classdetails != null
            boolean classdetailsJavaLangObjectNull = false;



            classdetailsJavaLangObjectNull = (classdetails) != (null);
            if (classdetailsJavaLangObjectNull) {




                classdetails.setArea(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener txtCityandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of classdetails.city
            //         is classdetails.setCity((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(txtCity);
            // localize variables for thread safety
            // classdetails.city
            java.lang.String classdetailsCity = null;
            // classdetails
            com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;
            // classdetails != null
            boolean classdetailsJavaLangObjectNull = false;



            classdetailsJavaLangObjectNull = (classdetails) != (null);
            if (classdetailsJavaLangObjectNull) {




                classdetails.setCity(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener txtNameandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of classdetails.name
            //         is classdetails.setName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(txtName);
            // localize variables for thread safety
            // classdetails
            com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;
            // classdetails.name
            java.lang.String classdetailsName = null;
            // classdetails != null
            boolean classdetailsJavaLangObjectNull = false;



            classdetailsJavaLangObjectNull = (classdetails) != (null);
            if (classdetailsJavaLangObjectNull) {




                classdetails.setName(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener txtPincodeandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of classdetails.pincode
            //         is classdetails.setPincode((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(txtPincode);
            // localize variables for thread safety
            // classdetails
            com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;
            // classdetails != null
            boolean classdetailsJavaLangObjectNull = false;
            // classdetails.pincode
            java.lang.String classdetailsPincode = null;



            classdetailsJavaLangObjectNull = (classdetails) != (null);
            if (classdetailsJavaLangObjectNull) {




                classdetails.setPincode(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private android.databinding.InverseBindingListener txtStateandroidTextAttrChanged = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of classdetails.state
            //         is classdetails.setState((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = android.databinding.adapters.TextViewBindingAdapter.getTextString(txtState);
            // localize variables for thread safety
            // classdetails.state
            java.lang.String classdetailsState = null;
            // classdetails
            com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;
            // classdetails != null
            boolean classdetailsJavaLangObjectNull = false;



            classdetailsJavaLangObjectNull = (classdetails) != (null);
            if (classdetailsJavaLangObjectNull) {




                classdetails.setState(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public ActivityKidosPartnersClassDetailsBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds);
        this.labelAddress = (android.widget.TextView) bindings[8];
        this.labelArea = (android.widget.TextView) bindings[9];
        this.labelCity = (android.widget.TextView) bindings[10];
        this.labelName = (android.widget.TextView) bindings[7];
        this.labelPincode = (android.widget.TextView) bindings[12];
        this.labelState = (android.widget.TextView) bindings[11];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.txtAddress = (android.widget.EditText) bindings[2];
        this.txtAddress.setTag(null);
        this.txtArea = (android.widget.EditText) bindings[3];
        this.txtArea.setTag(null);
        this.txtCity = (android.widget.EditText) bindings[4];
        this.txtCity.setTag(null);
        this.txtName = (android.widget.EditText) bindings[1];
        this.txtName.setTag(null);
        this.txtPincode = (android.widget.EditText) bindings[6];
        this.txtPincode.setTag(null);
        this.txtState = (android.widget.EditText) bindings[5];
        this.txtState.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
            case BR.classdetails :
                setClassdetails((com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean) variable);
                return true;
        }
        return false;
    }

    public void setClassdetails(com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean Classdetails) {
        this.mClassdetails = Classdetails;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.classdetails);
        super.requestRebind();
    }
    public com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean getClassdetails() {
        return mClassdetails;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String classdetailsPincode = null;
        java.lang.String classdetailsState = null;
        java.lang.String classdetailsArea = null;
        java.lang.String classdetailsAddressline1 = null;
        java.lang.String classdetailsCity = null;
        java.lang.String classdetailsName = null;
        com.androidapp.kidospartners.beans.KidosPartnersClassDetailsBean classdetails = mClassdetails;

        if ((dirtyFlags & 0x3L) != 0) {



                if (classdetails != null) {
                    // read classdetails.pincode
                    classdetailsPincode = classdetails.getPincode();
                    // read classdetails.state
                    classdetailsState = classdetails.getState();
                    // read classdetails.area
                    classdetailsArea = classdetails.getArea();
                    // read classdetails.addressline1
                    classdetailsAddressline1 = classdetails.getAddressline1();
                    // read classdetails.city
                    classdetailsCity = classdetails.getCity();
                    // read classdetails.name
                    classdetailsName = classdetails.getName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtAddress, classdetailsAddressline1);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtArea, classdetailsArea);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtCity, classdetailsCity);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtName, classdetailsName);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtPincode, classdetailsPincode);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.txtState, classdetailsState);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.txtAddress, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, txtAddressandroidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.txtArea, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, txtAreaandroidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.txtCity, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, txtCityandroidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.txtName, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, txtNameandroidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.txtPincode, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, txtPincodeandroidTextAttrChanged);
            android.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.txtState, (android.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (android.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, txtStateandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityKidosPartnersClassDetailsBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityKidosPartnersClassDetailsBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityKidosPartnersClassDetailsBinding>inflate(inflater, com.androidapp.kidospartners.R.layout.activity_kidos_partners_class_details, root, attachToRoot, bindingComponent);
    }
    public static ActivityKidosPartnersClassDetailsBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityKidosPartnersClassDetailsBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.androidapp.kidospartners.R.layout.activity_kidos_partners_class_details, null, false), bindingComponent);
    }
    public static ActivityKidosPartnersClassDetailsBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityKidosPartnersClassDetailsBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_kidos_partners_class_details_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityKidosPartnersClassDetailsBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): classdetails
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}