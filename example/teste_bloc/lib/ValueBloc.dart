import 'package:bloc_pattern/bloc_pattern.dart';

class ValueBloc extends BlocBase{
  int value=0;

  onChangeValue(int v) {
    value = v;
    notifyListeners();
  }


}