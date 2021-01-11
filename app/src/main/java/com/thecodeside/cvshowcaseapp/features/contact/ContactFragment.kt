package com.thecodeside.cvshowcaseapp.features.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.thecodeside.cvshowcaseapp.R
import com.thecodeside.cvshowcaseapp.common.ui.ViewBindingFragment
import com.thecodeside.cvshowcaseapp.common.utils.IntentUtils
import com.thecodeside.cvshowcaseapp.databinding.FragmentContactBinding


// TODO: 1/10/21 implement ContactFragment
class ContactFragment : ViewBindingFragment<FragmentContactBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.run {
                email.setOnClickListener {
                    IntentUtils.getForMail(resources.getString(R.string.email_addres)).also {
                        startActivity(it)
                    }
                }
                website.setOnClickListener {
                    IntentUtils.getForBrowser(resources.getString(R.string.website_url)).also {
                        startActivity(it)
                    }
                }
                linkedin.setOnClickListener {
                    IntentUtils.getForBrowser(resources.getString(R.string.linkedin_url)).also {
                        startActivity(it)
                    }
                }
                stackoverflow.setOnClickListener {
                    IntentUtils.getForBrowser(resources.getString(R.string.stackoverflow_url)).also {
                        startActivity(it)
                    }
                }
                medium.setOnClickListener {
                    IntentUtils.getForBrowser(resources.getString(R.string.medium_url)).also {
                        startActivity(it)
                    }
                }
            }
        }
    }

    override fun inflateView(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentContactBinding.inflate(inflater, parent, false)
}
