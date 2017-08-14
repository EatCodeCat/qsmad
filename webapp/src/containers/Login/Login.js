import React, {Component, PropTypes} from 'react';
import {Form, Input, Button} from 'antd';

import './Login.scss';

const createForm = Form.create;
const FormItem = Form.Item;

function noop() {
    return false;
}

class Login extends Component {
    static propTypes = {
        form: PropTypes.object.isRequired,
    }
    handleSubmit(){

    }
    render() {
        const {getFieldDecorator} = this.props.form;
        const emailProps = getFieldDecorator('email', {
            validate: [{
                rules: [
                    {required: true}
                ],
                trigger: 'onBlur'
            }, {
                rules: [
                    {required: true, message: '请输入正确的账号'}
                ],
                trigger: ['onBlur', 'onChange']
            }]
        });

        const passwordProps = getFieldDecorator('password', {
            rules: [
                {required: true, message: '密码不能为空'}
            ]
        });

        return (
            <div className="login-container">
                <div className="login-mask"/>
                <Form className="login-content" layout="horizontal" onSubmit={this.handleSubmit}>
                    <h2>qsm广告自动投注管理</h2>
                    <FormItem label="账号" hasFeedback>
                        {emailProps(
                            <Input
                                placeholder="请输入账号"
                                type="text"
                            />
                        )}
                    </FormItem>
                    <FormItem label="密码" hasFeedback>
                        {
                            passwordProps(
                                <Input
                                    type="password"
                                    autoComplete="off"
                                    placeholder="请输入密码"
                                    onContextMenu={noop} onPaste={noop} onCopy={noop} onCut={noop}
                                />
                            )
                        }
                    </FormItem>
                    <FormItem>
                        <Button className="ant-col-24" type="primary" htmlType="submit">登录</Button>
                    </FormItem>
                </Form>
            </div>
        );
    }
}

export default createForm()(Login);
