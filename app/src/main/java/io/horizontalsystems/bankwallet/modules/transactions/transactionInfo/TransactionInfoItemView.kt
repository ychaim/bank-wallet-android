package io.horizontalsystems.bankwallet.modules.transactions.transactionInfo

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import io.horizontalsystems.bankwallet.R
import io.horizontalsystems.bankwallet.modules.transactions.TransactionStatus
import kotlinx.android.synthetic.main.view_transaction_info_item.view.*

class TransactionInfoItemView : ConstraintLayout {

    private var attrTitle: String? = null
    private var attrValue: String? = null
    private var attrValueSubtitle: String? = null
    private var attrShowValueBackground: String? = null
    private var attrValueIcon: String? = null


    constructor(context: Context) : super(context) {
        initializeViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeViews()
        loadAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeViews()
        loadAttributes(attrs)
    }

    private fun initializeViews() {
        ConstraintLayout.inflate(context, R.layout.view_transaction_info_item, this)
    }

    fun bindAddress(title: String? = null, address: String? = null, showBottomBorder: Boolean = false) {
        txtTitle.text = title
        address?.let { addressView.bind(it) }
        addressView.visibility = if (address == null) View.GONE else View.VISIBLE
        border.visibility = if (showBottomBorder) View.VISIBLE else View.GONE

        invalidate()
    }

    fun bindTime(title: String? = null, time: String? = null) {
        txtTitle.text = title
        valueText.text = time
        valueText.visibility = if (time == null) View.GONE else View.VISIBLE
        border.visibility = View.VISIBLE
    }

    fun bindStatus(transactionStatus: TransactionStatus) {
        transactionStatusView.visibility = View.VISIBLE
        border.visibility = View.VISIBLE
        txtTitle.setText(R.string.TransactionInfo_Status)
        transactionStatusView.bind(transactionStatus)
        invalidate()
    }

    private fun loadAttributes(attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TransactionInfoItemView, 0, 0)
        try {
            attrTitle = ta.getString(R.styleable.TransactionInfoItemView_title)
            attrValue = ta.getString(R.styleable.TransactionInfoItemView_value)
            attrValueSubtitle = ta.getString(R.styleable.TransactionInfoItemView_valueSubtitle)
            attrShowValueBackground = ta.getString(R.styleable.TransactionInfoItemView_showValueBackground)
            attrValueIcon = ta.getString(R.styleable.TransactionInfoItemView_valueIcon)
        } finally {
            ta.recycle()
        }
    }

}
