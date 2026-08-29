import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:xiao_mi_push_plugin/xiao_mi_push_plugin.dart';
import 'package:xiao_mi_push_plugin/xiao_mi_push_plugin_listener.dart';

void main() => runApp(const MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final TextEditingController _outputController = TextEditingController();
  late final Map<String, Future<void> Function()> _methods;

  @override
  void initState() {
    super.initState();
    _methods = <String, Future<void> Function()>{
      'init': () => XiaoMiPushPlugin.init(
        appId: '2882303761518406102',
        appKey: '5981840633102',
      ),
      'setAlias': () =>
          XiaoMiPushPlugin.setAlias(alias: 'test', category: 'test'),
      'unsetAlias': () =>
          XiaoMiPushPlugin.unsetAlias(alias: 'test', category: 'test'),
      'getAllAlias': () => _showResult(XiaoMiPushPlugin.getAllAlias()),
      'setUserAccount': () => XiaoMiPushPlugin.setUserAccount(
        userAccount: 'test',
        category: 'test',
      ),
      'unsetUserAccount': () => XiaoMiPushPlugin.unsetUserAccount(
        userAccount: 'test',
        category: 'test',
      ),
      'getAllUserAccount': () =>
          _showResult(XiaoMiPushPlugin.getAllUserAccount()),
      'subscribe': () =>
          XiaoMiPushPlugin.subscribe(topic: 'test', category: 'test'),
      'unsubscribe': () =>
          XiaoMiPushPlugin.unsubscribe(topic: 'test', category: 'test'),
      'getAllTopic': () => _showResult(XiaoMiPushPlugin.getAllTopic()),
      'getRegId': () => _showResult(XiaoMiPushPlugin.getRegId()),
      'getSdkVersion': () => _showResult(XiaoMiPushPlugin.getSdkVersion()),
    };

    XiaoMiPushPlugin.addListener(_onXiaoMiPushListener);
  }

  Future<void> _showResult(Future<Object?> result) async {
    _outputController.text = jsonEncode(await result);
  }

  void _onXiaoMiPushListener(XiaoMiPushListenerTypeEnum type, Object? params) {
    _outputController.text =
        '''
======================
Listener ${type.name}:
--------------------------------------------
${jsonEncode(params)}
======================''';
  }

  @override
  void dispose() {
    XiaoMiPushPlugin.removeListener(_onXiaoMiPushListener);
    _outputController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Xiaomi Push plugin example')),
        body: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: <Widget>[
              TextField(
                controller: _outputController,
                maxLines: 10,
                readOnly: true,
              ),
              const SizedBox(height: 16),
              Expanded(
                child: SingleChildScrollView(
                  child: Wrap(
                    runSpacing: 10,
                    spacing: 10,
                    children: _methods.entries
                        .map(
                          (MapEntry<String, Future<void> Function()> entry) =>
                              OutlinedButton(
                                onPressed: entry.value,
                                child: Text(entry.key),
                              ),
                        )
                        .toList(),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
