import 'package:flutter_test/flutter_test.dart';
import 'package:xiao_mi_push_plugin_example/main.dart';

void main() {
  testWidgets('shows the plugin actions', (WidgetTester tester) async {
    await tester.pumpWidget(const MyApp());

    expect(find.text('getRegId'), findsOneWidget);
    expect(find.text('getSdkVersion'), findsOneWidget);
  });
}
