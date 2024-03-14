import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.movieapp.core.presentation.navigation.BottomNavItem
import br.com.movieapp.core.util.Constants.MOVIE_DETAIL_ARGUMENT_KEY
import br.com.movieapp.movieDetailFeature.presentation.MovieDetailScreen
import br.com.movieapp.movieDetailFeature.presentation.MovieDetailViewModel
import br.com.movieapp.moviePopularFeature.presentation.MoviePopularScreen
import br.com.movieapp.moviePopularFeature.presentation.MoviePopularViewModel
import br.com.movieapp.searchMovieFeature.presentation.MovieSearchEvent
import br.com.movieapp.searchMovieFeature.presentation.MovieSearchScreen
import br.com.movieapp.searchMovieFeature.presentation.MovieSearchViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MoviePopular.route
    ) {
        composable(BottomNavItem.MoviePopular.route) {
            val viewModel: MoviePopularViewModel = hiltViewModel()
            val uiState = viewModel.uiState

            MoviePopularScreen(
                uiState = uiState,
                navigateToDetailMovie = {
                    navController.navigate(BottomNavItem.MovieDetails.passMovieId(it))
                })
        }
        composable(BottomNavItem.MovieSearch.route) {
            val viewModel: MovieSearchViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val onEvent: (MovieSearchEvent) -> Unit = viewModel::event
            val onFetch: (String) -> Unit = viewModel::fetch

            MovieSearchScreen(
                uiState = uiState,
                onEvent = onEvent,
                onFetch = onFetch,
                navigateToDetailMovie = {
                    navController.navigate(BottomNavItem.MovieDetails.passMovieId(it))
                }
            )
        }
        composable(BottomNavItem.MovieFavorite.route) {

        }

        composable(
            route = BottomNavItem.MovieDetails.route,
            arguments = listOf(
                navArgument(MOVIE_DETAIL_ARGUMENT_KEY){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            val viewModel: MovieDetailViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val getMovieDetail = viewModel::getMovieDetail

            MovieDetailScreen(
                id = it.arguments?.getInt(MOVIE_DETAIL_ARGUMENT_KEY),
                uiState = uiState,
                getMovieDetail = getMovieDetail
            )
        }
    }
}