package yehor.tkachuk.mycv.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import yehor.tkachuk.mycv.R
import yehor.tkachuk.mycv.model.CVProfile
import yehor.tkachuk.mycv.model.EducationItem
import yehor.tkachuk.mycv.model.Project
import yehor.tkachuk.mycv.ui.navigation.navigateToProject
import yehor.tkachuk.mycv.ui.theme.montserrat
import yehor.tkachuk.mycv.ui.theme.textColor
import yehor.tkachuk.mycv.ui.theme.textColorBlue
import yehor.tkachuk.mycv.ui.theme.textColorLightBlue

@Composable
fun HomeScreen(navController: NavController, profile: CVProfile) {
    val toolbarState = rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current
    CollapsingToolbarScaffold(
        modifier = Modifier,
        state = toolbarState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile_picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .parallax(0.2f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .road(
                        whenCollapsed = Alignment.BottomStart,
                        whenExpanded = Alignment.BottomStart
                    )
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                textColorBlue.copy(alpha = 1 - toolbarState.toolbarState.progress),
                                textColorBlue
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = profile.name,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
                Text(
                    text = profile.position,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }

        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(), true)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryView(named = "About me") {
                Text(text = profile.about)
            }
            CategoryView(named = "Education") {
                for (item in profile.eduList) {
                    EducationView(educationItem = item)
                }
            }
            CategoryView(named = "Languages I speak") {
                Spacer(modifier = Modifier.height(8.dp))
                GridView(items = profile.languages) { language ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(false),
                            onClick = {
                                Toast.makeText(context, language.name, Toast.LENGTH_SHORT).show()
                            }
                        )
                    ) {
                        AsyncImage(
                            model = language.getFlagImageUrl(),
                            contentDescription = "flag_${language.code}",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(32.dp, 22.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Text(text = language.level)
                    }
                }
            }
            CategoryView(named = "Projects") {
                for (project in profile.projectList) {
                    ProjectItem(project = project, onClick = {
                        navController.navigateToProject(it.id)
                    })
                }
            }
        }
    }
}

@Composable
fun <T> GridView(columns: Int = 2, items: List<T>, itemComposable: @Composable (T) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val rowsCount =
            (items.size / columns).let { if (it * columns != items.size) it + 1 else it }
        for (rowIndex in 0 until rowsCount) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                for (column in 0 until columns) {
                    val index = rowIndex * columns + column
                    if (index < items.size) {
                        itemComposable(items[index])
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectItem(project: Project, onClick: (Project) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = {
                    onClick(project)
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .wrapContentHeight(), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = project.title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(text = project.descriptionShort, fontSize = 12.sp)
                Text(text = "as ${project.position}", fontSize = 12.sp, color = textColorLightBlue)
            }
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
                contentDescription = "arrow_forward"
            )
        }
        Divider(color = textColorLightBlue)
    }
}

@Composable
fun CategoryView(named: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = named,
            color = textColorBlue,
            fontFamily = montserrat,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        content()
    }
}

@Composable
fun EducationView(educationItem: EducationItem) {
    Column(
    ) {
        Text(text = educationItem.name, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = educationItem.degree,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(8.dp))
        TaggedText(tag = "Faculty", text = educationItem.faculty)
        Spacer(modifier = Modifier.height(8.dp))
        TaggedText(tag = "Speciality", text = educationItem.speciality)
    }
}

@Composable
fun TaggedText(tag: String, text: String) {
    Column() {
        Text(
            text = tag,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = textColorLightBlue
        )
        Text(text = text, fontWeight = FontWeight.Normal, fontSize = 14.sp, color = textColor)
    }
}