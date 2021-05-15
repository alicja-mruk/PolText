package com.put.pt.poltext.screens.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.put.pt.poltext.R
import com.put.pt.poltext.databinding.ActivityHomeBinding
import com.put.pt.poltext.model.User
import com.put.pt.poltext.navigator.ClickListener
import com.put.pt.poltext.navigator.NavigationItems
import com.put.pt.poltext.navigator.NavigationRVAdapter
import com.put.pt.poltext.navigator.RecyclerTouchListener
import com.put.pt.poltext.screens.BaseActivity
import com.put.pt.poltext.screens.home.private_chat.ChatPrivateFragmentLobby
import com.put.pt.poltext.screens.home.private_chat.ChatPrivateFragmentLobby.Companion.USER_KEY
import com.put.pt.poltext.screens.home.profile.EditProfileFragment
import com.put.pt.poltext.screens.home.profile.ProfileFragment
import com.put.pt.poltext.screens.home.public_chat.PublicChatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class HomeActivity : BaseActivity(), ProfileFragment.Listener, EditProfileFragment.Listener, ChatPrivateFragmentLobby.Listener {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter

    private val viewModel by viewModel<PublicChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment_home)

        binding.navigationRv.layoutManager = LinearLayoutManager(this)
        binding.navigationRv.setHasFixedSize(true)
        updateAdapter(0)
        addOnTouchListener()
        bindOnClickListeners()
    }

    private fun addOnTouchListener() {
        binding.navigationRv.addOnItemTouchListener(RecyclerTouchListener(this, object :
            ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        navController.navigate(R.id.chatPrivateFragmentLobby)
                    }
                    1 -> {
                        navController.navigate(R.id.chatPublicFragment)
                    }
                    2 -> {
                        navController.navigate(R.id.profileFragment)
                    }
                }
                updateAdapter(position)

                Handler().postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))
    }

    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(NavigationItems.data, highlightItemPos)
        binding.navigationRv.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun bindOnClickListeners() {
        binding.toolbar.settingsBtn.setOnClickListener {
            navController.navigateUp()
            navController.navigate(R.id.settingsFragment)
        }
        binding.toolbar.hamburgerBtn.setOnClickListener {
            if (binding.drawerLayout.isOpen) {
                binding.drawerLayout.closeDrawers()
            } else {
                binding.drawerLayout.open()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment

        when (navHostFragment.childFragmentManager.fragments[0]) {
            is EditProfileFragment -> {
                navHostFragment.navController.navigate(R.id.action_editProfileFragment_to_profileFragment)
                return
            }
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (!navController.popBackStack()) {
                finish()
                return
            }
            navController.popBackStack()
        }
    }

    override fun onNavigateToEditProfileScreen() {
        navController.navigate(R.id.editProfileFragment)
    }

    override fun onNavigateToProfileScreen() {
        navController.navigate(R.id.profileFragment)
    }

    override fun onNavigateToChatPublicFragment(user: User) {
        val bundle = Bundle()
        bundle.putSerializable(USER_KEY, user)
        navController.navigate(R.id.chatPrivateFragment, bundle)
    }
}