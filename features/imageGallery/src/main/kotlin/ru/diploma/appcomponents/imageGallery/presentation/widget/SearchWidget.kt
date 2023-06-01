package ru.diploma.appcomponents.imageGallery.presentation.widget

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.*
import androidx.glance.action.actionStartActivity

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import ru.diploma.appcomponents.core.MainActivity
import ru.diploma.appcomponents.core.theme.spacing

object AppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        Row(
            verticalAlignment = Alignment.Vertical.CenterVertically,
            modifier = GlanceModifier.background(GlanceTheme.colors.background)
                .wrapContentWidth()
                .padding(horizontal = 6.dp, vertical = 8.dp)
                .cornerRadius(20.dp),
        ) {
            Image(
                modifier = GlanceModifier.size(50.dp),
                provider = ImageProvider(ru.diploma.appcomponents.core.R.drawable.ic_logo_new),
                contentDescription = "app logo"
            )
            Spacer(GlanceModifier.width(8.dp))
            Button(
                text = "Go to app",
                modifier = GlanceModifier.width(60.dp),
                onClick = actionStartActivity<MainActivity>(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorProvider(
                        Color(
                            0xFFbfabfd
                        )
                    )
                )
            )
        }
    }
}

class AppWidgetReceiver(override val glanceAppWidget: GlanceAppWidget = AppWidget) :
    GlanceAppWidgetReceiver()