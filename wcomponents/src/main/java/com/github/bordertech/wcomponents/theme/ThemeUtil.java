package com.github.bordertech.wcomponents.theme;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.util.Config;
import com.github.bordertech.wcomponents.util.DebugUtil;
import com.github.bordertech.wcomponents.util.StreamUtil;
import com.github.bordertech.wcomponents.util.Util;

/**
 * Static support classes for retrieving the theme version.
 * 
 * @author Martin Shevchenko
 * @since 1.0.0
 */
public final class ThemeUtil
{
    /** The logger instance for this class. */
    private static final Log log = LogFactory.getLog(ThemeUtil.class);

    /** The parameter key for the theme name. */
    private static final String THEME_PARAM = "wcomponent.theme.name";

    /** The theme build version number. */
    private static final String THEME_BUILD;

    /** The theme version properties file name. */
    private static final String THEME_VERSION_FILE_NAME = "version.properties";

    /** The theme build property name. */
    private static final String THEME_BUILD_NUMBER_PARAM = "build.number";

    /** The theme wcomponent version property name. */
    private static final String THEME_WC_BUILD_NUMBER_PARAM = "wc.project.version";

    static
    {
        // Get theme name
        String name = getThemeName();

        // Load theme build (depends on the theme name)
        String resourceName = "/theme/" + name + '/' + THEME_VERSION_FILE_NAME;

        // Get theme version property file (if in classpath)
        InputStream resourceStream = null;
        Properties prop = new Properties();
        String themeBuild = null;
        String themeWcVersion = null;
        try
        {
            resourceStream = ThemeUtil.class.getResourceAsStream(resourceName);
            prop.load(resourceStream);
            // Theme build property
            themeBuild = prop.getProperty(THEME_BUILD_NUMBER_PARAM);
            // WComponent build theme depends on property
            themeWcVersion = prop.getProperty(THEME_WC_BUILD_NUMBER_PARAM);
        }
        catch (Exception e)
        {
            log.warn("Could not load theme version properties file \"" + resourceName + "\"");
        }
        finally
        {
            StreamUtil.safeClose(resourceStream);
        }

        // If theme build not available, then use the wcomponents project version
        if (themeBuild == null)
        {
            // The theme build number is used to "bust" the cache. As the theme build may not always be available in the
            // classpath (ie the theme is served up from a different server) we at least want to use the wcomponent
            // version to try and bust the cache. However, most projects will have the theme in the classpath.
            log.warn(THEME_BUILD_NUMBER_PARAM + " property not found in \"" + resourceName
                     + "\". Will use wcomponent project version.");
            THEME_BUILD = "wc-" + WebUtilities.getProjectVersion();
        }
        else
        {
            THEME_BUILD = themeBuild;
        }

        log.info("Using theme \"" + name + "\"" + " build \"" + THEME_BUILD + "\"");

        // Check the theme wcomponent version against the project wcomponent version
        if (themeWcVersion != null)
        {
            String wcProject = WebUtilities.getProjectVersion();
            if (!Util.equals(themeWcVersion, wcProject))
            {
                log.warn("The theme wcomponent version \"" + themeWcVersion
                         + "\" does not match the project wcomponent version \"" + wcProject + "\".");
            }
        }
    }

    /** Prevent instantiation of this class. */
    private ThemeUtil()
    {
    }

    /**
     * @return the current theme name
     */
    public static String getThemeName()
    {
        return Config.getInstance().getString(THEME_PARAM);
    }

    /**
     * @return the current theme build
     */
    public static String getThemeBuild()
    {
        return THEME_BUILD;
    }

    /**
     * <p>
     * Retrieves the complete path to the theme's XSLT. This method takes the current theme and user's locale into
     * account.
     * </p>
     * <p>
     * Note: The XSLT is the single integration point to the client-side rendering.
     * </p>
     * 
     * @param uic the current user's UIContext.
     * @return the theme XSLT.
     */
    public static String getThemeXslt(final UIContext uic)
    {
        String themePath = uic.getEnvironment().getThemePath();
        boolean hasQueryString = themePath.indexOf('?') != -1;
        Locale locale = uic.getLocale();

        StringBuffer path = new StringBuffer(themePath.length() + 20);

        // Path
        if (themePath.length() > 0 && !themePath.endsWith("/") && !themePath.endsWith("="))
        {
            path.append(themePath).append("/xslt/");
        }
        else
        {
            path.append(themePath).append("xslt/");
        }

        // Build file name....
        path.append("all");

        // Locale
        if (locale != null)
        {
            path.append('_').append(locale.getLanguage());

            if (!Util.empty(locale.getCountry()))
            {
                path.append('-').append(locale.getCountry());

                if (!Util.empty(locale.getVariant()))
                {
                    path.append('-').append(locale.getVariant());
                }
            }
        }

        // Debug
        if (DebugUtil.isDebugStructureEnabled())
        {
            path.append("_debug");
        }

        // xsl
        path.append(".xsl");

        // Add cache busting suffix
        String build = getThemeBuild();
        String themeName = getThemeName();

        path.append(hasQueryString ? '&' : '?').append("build=").append(WebUtilities.escapeForUrl(build))
            .append("&theme=").append(WebUtilities.escapeForUrl(themeName));

        return path.toString();
    }

}