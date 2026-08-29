import 'dart:convert';

class MiPushCommandMessageEntity {
  final String? command;
  final List<String>? commandArguments;
  final int? resultCode;

  MiPushCommandMessageEntity({
    this.command,
    this.commandArguments,
    this.resultCode,
  });

  factory MiPushCommandMessageEntity.fromJson(Map<String, dynamic> json) {
    return MiPushCommandMessageEntity(
      command: json['command'],
      commandArguments: (json['commandArguments'] as List<dynamic>?)
          ?.cast<String>(),
      resultCode: json['resultCode'],
    );
  }

  Map<String, dynamic> toJson() => {
    'command': command,
    'commandArguments': commandArguments != null
        ? jsonEncode(commandArguments)
        : null,
    'resultCode': resultCode,
  };
}
