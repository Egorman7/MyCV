package yehor.tkachuk.mycv.ui.screen.project


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import yehor.tkachuk.mycv.R
import yehor.tkachuk.mycv.model.Project
import yehor.tkachuk.mycv.ui.screen.home.CategoryView
import yehor.tkachuk.mycv.ui.theme.textColorBlue
import yehor.tkachuk.mycv.ui.theme.textColorLightBlue

@Composable
fun ProjectScreen(navController: NavController, project: Project) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                    contentDescription = "back"
                )
            }
            Text(text = project.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "${project.startDate} - ${project.endDate}",
                fontSize = 12.sp,
                color = textColorLightBlue,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            CategoryView(named = "About project") {
                Text(text = project.description)
            }
            CategoryView(named = "Technologies") {
                Text(text = project.technologies)
            }

            if (project.googlePlayLink.isNotBlank()) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp)
                        .clickable(interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(), onClick = {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(project.googlePlayLink)
                                    )
                                )
                            })
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_uim_google_play),
                        contentDescription = "gp_icon",
                        modifier = Modifier.size(24.dp, 24.dp)
                    )
                    Text(
                        text = "See on Google Play",
                        color = textColorBlue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

    }
}