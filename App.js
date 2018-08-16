import React,{Component} from 'react';
import {
    Text,
    View,
    StyleSheet,
    DeviceEventEmitter,
    NativeModules,
} from 'react-native';
import Toast, {DURATION} from 'react-native-easy-toast'

export default class App extends Component{
    constructor(props){
        super(props);
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.welcome}
                      onPress={this.getTimeWithDeviceEventEmitter.bind(this)}
                >
                    RCTDeviceEventEmitter获取时间
                </Text>
                <Text style={styles.welcome}
                      onPress={this.getTimeWithCallBack.bind(this)}
                >
                    CallBack获取时间
                </Text>
                <Text style={styles.welcome}
                      onPress={this.getTimeWithPromise.bind(this)}
                >
                    Promise获取时间
                </Text>

                <Toast ref="toast"/>
            </View>
        );
    }

    componentWillMount() {
        DeviceEventEmitter.addListener('rnTransMisson', 
           (msg) => {
        
               this.refs.toast.show("DeviceEventEmitter收到消息:" + "\n" + "年龄:" + msg.age + "时间:" + msg.time, 2000);
        });
    }

    getTimeWithDeviceEventEmitter() {
        NativeModules.TransMissonMoudle.contactWithRTC("getTime");
    }


    getTimeWithCallBack() {
        NativeModules.TransMissonMoudle.contactWithCallBack("getTime",
            (msg) => {

                this.refs.toast.show("CallBack收到消息:" + "\n" + msg, 2000);
            }
        );

    }

    getTimeWithPromise() {
        NativeModules.TransMissonMoudle.contactWithPromise("getTime")
            .then(msg=> {

                this.refs.toast.show("Promise收到消息:" + "\n" + "年龄:" + msg.age + "时间:" + msg.time, 2000);
                
            }).catch(error=> {
                console.log(error);
            });
    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
