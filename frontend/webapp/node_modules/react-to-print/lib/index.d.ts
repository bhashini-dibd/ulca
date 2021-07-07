import * as React from "react";
export interface IPrintContextProps {
    handlePrint: () => void;
}
export declare const PrintContextConsumer: React.Consumer<IPrintContextProps> | (() => null);
export interface ITriggerProps<T> {
    onClick: () => void;
    ref: (v: T) => void;
}
declare type Font = {
    family: string;
    source: string;
};
declare type PropertyFunction<T> = () => T;
export interface IReactToPrintProps {
    bodyClass?: string;
    content: () => React.ReactInstance | null;
    copyStyles?: boolean;
    documentTitle?: string;
    fonts?: Font[];
    onAfterPrint?: () => void;
    onBeforeGetContent?: () => void | Promise<any>;
    onBeforePrint?: () => void | Promise<any>;
    onPrintError?: (errorLocation: "onBeforeGetContent" | "onBeforePrint" | "print", error: Error) => void;
    pageStyle?: string | PropertyFunction<string>;
    print?: (target: HTMLIFrameElement) => Promise<any>;
    removeAfterPrint?: boolean;
    suppressErrors?: boolean;
    trigger?: <T>() => React.ReactElement<ITriggerProps<T>>;
}
export default class ReactToPrint extends React.Component<IReactToPrintProps> {
    private linkTotal;
    private linksLoaded;
    private linksErrored;
    private fontsLoaded;
    private fontsErrored;
    static defaultProps: {
        copyStyles: boolean;
        pageStyle: string;
        removeAfterPrint: boolean;
        suppressErrors: boolean;
    };
    startPrint: (target: HTMLIFrameElement) => void;
    triggerPrint: (target: HTMLIFrameElement) => void;
    handleClick: () => void;
    handlePrint: () => void;
    handleRemoveIframe: (force?: boolean | undefined) => void;
    render(): JSX.Element | null;
}
export declare const useReactToPrint: ((props: IReactToPrintProps) => () => void) | ((props: IReactToPrintProps) => undefined);
export {};
