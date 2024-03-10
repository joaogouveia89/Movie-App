import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.movieapp.core.presentation.navigation.BottomNavItem

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MoviePopular.route
    ){
        composable(BottomNavItem.MoviePopular.route){

        }
        composable(BottomNavItem.MovieSearch.route){

        }
        composable(BottomNavItem.MovieFavorite.route){

        }
    }
}