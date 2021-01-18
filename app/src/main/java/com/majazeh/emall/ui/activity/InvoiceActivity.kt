package com.majazeh.emall.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.ali74.libkot.recyclerview.EndlessRVScroll
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.InvoiceBiding
import com.majazeh.emall.ui.adapter.InvoiceAdapter
import com.majazeh.emall.ui.adapter.InvoiceDetailAdapter
import com.majazeh.emall.viewmodel.InvoiceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InvoiceActivity : BindingActivity<InvoiceBiding>() {

    private val vm by viewModel<InvoiceViewModel>()

    private var adapter: InvoiceAdapter? = null
    private var pageaction = 1

    override fun getLayoutResId(): Int = R.layout.activity_invoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        val manager = LinearLayoutManager(this)
        binding.rclInvoice.layoutManager = manager

        binding.rclInvoice.addOnScrollListener(object : EndlessRVScroll(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.prbLazyLoad.visibility = View.VISIBLE
                pageaction = page + 1
                vm.getInvoice(pageaction)
            }
        })

        vm.invoiceDetail.observe(this, {
            val adapter = InvoiceDetailAdapter(it.details)
            MaterialAlertDialogBuilder(this)
                .setAdapter(adapter) { _, _ -> }
                .setNeutralButton(R.string.closeDialog) { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        })
        vm.invoice.observe(this, {
            if (pageaction == 1) {
                if (!it.isNullOrEmpty()) {
                    adapter = InvoiceAdapter(it, vm)
                    binding.rclInvoice.adapter = adapter
                    binding.txtNull.visibility = View.GONE
                } else binding.txtNull.visibility = View.VISIBLE
            } else {
                adapter?.addAll(it)
            }
        })
        vm.isLoading.observe(this, {
            if (it) {
                if (pageaction == 1)
                    progressDialog.show()
                else
                    binding.prbLazyLoad.visibility = View.VISIBLE
            } else {
                binding.prbLazyLoad.visibility = View.INVISIBLE
                progressDialog.dismiss()
            }
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_menu).setIcon(R.drawable.ic_back)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_menu) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}