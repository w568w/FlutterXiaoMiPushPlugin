package top.huic.xiao_mi_push_plugin

import android.content.Context
import androidx.annotation.NonNull
import com.xiaomi.push.BuildConfig as MiPushBuildConfig
import com.xiaomi.mipush.sdk.MiPushClient
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import top.huic.xiao_mi_push_plugin.util.CommonUtil
import java.lang.reflect.Method

/** XiaoMiPushPlugin */
public class XiaoMiPushPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var context: Context
    companion object {
        lateinit var channel: MethodChannel
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPluginBinding) {
        this.context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xiao_mi_push_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        try {
            val method: Method = this.javaClass.getDeclaredMethod(call.method, MethodCall::class.java, Result::class.java)
            method.invoke(this, call, result)
        } catch (e: Exception) {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    /**
     * 初始化
     */
    private fun init(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val appId = CommonUtil.getParam<String>(call, result, "appId")
        val appKey = CommonUtil.getParam<String>(call, result, "appKey")

        MiPushClient.registerPush(this.context, appId, appKey)
        result.success(null)
    }

    /**
     * 设置别名
     */
    private fun setAlias(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val alias = CommonUtil.getParam<String>(call, result, "alias")
        val category = CommonUtil.getParam<String>(call, result, "category")

        MiPushClient.setAlias(this.context, alias, category)
        result.success(null)
    }

    /**
     * 撤销别名
     */
    private fun unsetAlias(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val alias = CommonUtil.getParam<String>(call, result, "alias")
        val category = CommonUtil.getParam<String>(call, result, "category")

        MiPushClient.unsetAlias(this.context, alias, category)
        result.success(null)
    }

    /**
     * 获得所有别名
     */
    private fun getAllAlias(@NonNull call: MethodCall, @NonNull result: Result) {
        result.success(MiPushClient.getAllAlias(context))
    }

    /**
     * 设置用户账户
     */
    private fun setUserAccount(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val userAccount = CommonUtil.getParam<String>(call, result, "userAccount")
        val category = CommonUtil.getParam<String>(call, result, "category")

        MiPushClient.setUserAccount(this.context, userAccount, category)
        result.success(null)
    }

    /**
     * 撤销设置用户账户
     */
    private fun unsetUserAccount(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val userAccount = CommonUtil.getParam<String>(call, result, "userAccount")
        val category = CommonUtil.getParam<String>(call, result, "category")

        MiPushClient.unsetUserAccount(this.context, userAccount, category)
        result.success(null)
    }

    /**
     * 获得所有用户账户
     */
    private fun getAllUserAccount(@NonNull call: MethodCall, @NonNull result: Result) {
        result.success(MiPushClient.getAllUserAccount(context))
    }

    /**
     * 设置标签
     */
    private fun subscribe(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val topic = CommonUtil.getParam<String>(call, result, "topic")
        val category = CommonUtil.getParam<String>(call, result, "category")

        MiPushClient.subscribe(this.context, topic, category)
        result.success(null)
    }

    /**
     * 撤销设置标签
     */
    private fun unsubscribe(@NonNull call: MethodCall, @NonNull result: Result) {
        // 获得参数
        val topic = CommonUtil.getParam<String>(call, result, "topic")
        val category = CommonUtil.getParam<String>(call, result, "category")

        MiPushClient.unsubscribe(this.context, topic, category)
        result.success(null)
    }

    /**
     * 获得所有标签
     */
    private fun getAllTopic(@NonNull call: MethodCall, @NonNull result: Result) {
        result.success(MiPushClient.getAllTopic(context))
    }

    /**
     * 获取客户端的 RegId
     */
    private fun getRegId(@NonNull call: MethodCall, @NonNull result: Result) {
        result.success(MiPushClient.getRegId(context))
    }

    /**
     * 获取小米推送 SDK 版本
     */
    private fun getSdkVersion(@NonNull call: MethodCall, @NonNull result: Result) {
        result.success(MiPushBuildConfig.VERSION_NAME)
    }
}
