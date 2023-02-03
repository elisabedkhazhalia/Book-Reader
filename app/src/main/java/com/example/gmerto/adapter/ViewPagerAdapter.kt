package com.example.gmerto.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gmerto.Fragments.ShesvlaFragment
import com.example.gmerto.FragmentsProfile.LibraryFragment
import com.example.gmerto.FragmentsProfile.ProfileFragment

class ViewPagerAdapter(activity: ShesvlaFragment):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        return if(position==0){
            LibraryFragment()
        }else{
            ProfileFragment()
        }
    }

}